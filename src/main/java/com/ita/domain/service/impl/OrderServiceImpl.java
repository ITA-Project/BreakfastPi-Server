package com.ita.domain.service.impl;

import com.ita.domain.dto.*;
import com.ita.domain.dto.common.PageResult;
import com.ita.domain.entity.*;
import com.ita.domain.enums.BoxStatusEnum;
import com.ita.domain.enums.OrderStatusEnum;
import com.ita.domain.error.BusinessException;
import com.ita.domain.error.ErrorResponseEnum;
import com.ita.domain.mapper.*;
import com.ita.domain.service.OrderService;
import com.ita.utils.IdWorker;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.ita.common.constant.Constant.FIRST;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private CartMapper cartMapper;
    @Resource
    private BoxMapper boxMapper;
    @Resource
    private IdWorker idWorker;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ShopMapper shopMapper;
    @Autowired
    private MqttMessageServiceImpl mqttMessageService;

    @Override
    public PageResult getUserOrders(Integer userId, int page, int pageSize, List<Integer> statusList) {
        int total = orderMapper.countUserOrders(userId, statusList);
        List<OrderDTO> orders = convertOrderDTO(orderMapper.getOrdersByUser(userId, statusList, (page - 1) * pageSize, pageSize));
        int totalPage = total / pageSize;
        if (total < pageSize && total != 0) {
            totalPage = 1;
        }
        return PageResult.builder().list(orders).total(total).totalPage(totalPage).build();
    }

    @Override
    public List<ProductDTO> getUserFavouriteProducts(Integer userId) {
        List<Integer> productIds = orderItemMapper.getUserTopTreeOrderItem(userId)
                .stream().map(OrderItem::getProductId).collect(Collectors.toList());
        return productMapper.selectAllByProductIds(productIds).stream().map(n -> {
            ProductDTO productDTO = ProductDTO.builder().build();
            BeanUtils.copyProperties(n, productDTO);
            return productDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderDetail(String orderNumber) throws BusinessException {
        Order order = orderMapper.selectByOrderNumber(orderNumber);
        if (Objects.isNull(order)) {
            throw new BusinessException(ErrorResponseEnum.ORDER_NOT_EXIST);
        }
        Box box = boxMapper.selectByPrimaryKey(order.getBoxId());
        List<OrderItem> orderItems = orderItemMapper.selectAllByOrderNumber(orderNumber);
        List<Integer> productIds = orderItems.stream().map(OrderItem::getProductId).collect(Collectors.toList());
        Map<Integer, Product> productMap = new HashMap<>();
        List<Product> products = productMapper.selectAllByProductIds(productIds);
        if (CollectionUtils.isEmpty(products)) {
            throw new BusinessException(ErrorResponseEnum.ORDER_DATA_INCORRECT);
        }
        Category category = categoryMapper.selectByPrimaryKey(products.get(0).getCategoryId());
        Shop shop = shopMapper.selectByPrimaryKey(category.getShopId());
        products.forEach(product -> productMap.put(product.getId(), product));
        List<OrderItemDTO> orderItemDTOs = orderItems.stream().map(orderItem -> OrderItemDTO.of(orderItem, ProductDTO.of(productMap.getOrDefault(orderItem.getProductId(), Product.builder().build()))))
                .collect(Collectors.toList());
        return OrderDTO.of(order, ShopDTO.of(shop), BoxDTO.of(box), orderItemDTOs);
    }

    @Transactional(rollbackFor = BusinessException.class)
    @Override
    public OrderDTO createOrder(Integer userId, String address, LocalDateTime expectedMealTime) throws BusinessException {
        /*
          1.获取用户购物车信息
          2.确定柜子号 // 加锁
          3.计算订单总价
          4.生成订单号
          5.扣减库存  //加锁 8.增加商品销量
          6.创建订单
          7.更新订单项
          9.清空购物车
         */

        List<Cart> cartList = cartMapper.selectByUserId(userId);
        if (CollectionUtils.isEmpty(cartList)) {
            throw new BusinessException(ErrorResponseEnum.CART_IS_EMPTY);
        }

        Box box = determineBox(address);

        List<OrderItemDTO> orderItemDTOList = generateOrderItemDTO(cartList);

        double orderTotalPrice = getOrderTotalPrice(orderItemDTOList);

        updateStockAndSale(cartList);

        Order order = Order.builder()
                .orderNumber(idWorker.nextId() + "")
                .amount(orderTotalPrice)
                .userId(userId)
                .boxId(box.getId())
                .estimatedTime(expectedMealTime)
                .status(OrderStatusEnum.NO_PAY.getCode())
                .build();
        orderMapper.insert(order);

        saveOrderItem(cartList, order.getOrderNumber());

        clearCart(cartList);

        return OrderDTO.of(order, BoxDTO.of(box), orderItemDTOList);
    }

    @Override
    public PageResult getOrdersByStatus(List<Integer> statusList, int page, int pageSize) {
        int total = orderMapper.countOrdersByStatus(statusList);
        List<OrderDTO> orders = convertOrderDTO(orderMapper.selectOrdersByStatus(statusList, (page - 1) * pageSize, pageSize));
        int totalPage = total / pageSize;
        if (total < pageSize && total != 0) {
            totalPage = 1;
        }
        return PageResult.builder().list(orders).total(total).totalPage(totalPage).build();
    }

    @Override
    public boolean updateStatusToDeliveredByOrders(List<Integer> orderIds){
        orderIds.stream().forEach(orderId -> {
            Order order = this.orderMapper.selectByPrimaryKey(orderId);
            if(order.getStatus().equals(OrderStatusEnum.SHIPPED.getCode())) {
                order.setStatus(OrderStatusEnum.DELIVERED.getCode());
                order.setDeliverTime(LocalDateTime.now());
                this.orderMapper.updateByPrimaryKey(order);
            }
            Box box = this.boxMapper.selectByPrimaryKey(order.getBoxId());
            box.setStatus(BoxStatusEnum.LOADED.getCode());
            this.boxMapper.updateByPrimaryKey(box);
        });
        return true;
    }

    @Override
    public boolean updateStatusToCompletedByOrders(List<Integer> orderIds){
        orderIds.stream().forEach(orderId -> {
            Order order = this.orderMapper.selectByPrimaryKey(orderId);
            if(order.getStatus().equals(OrderStatusEnum.DELIVERED.getCode())) {
                order.setStatus(OrderStatusEnum.COMPLETED.getCode());
                order.setCompletedTime(LocalDateTime.now());
                this.orderMapper.updateByPrimaryKey(order);
            }
            Box box = this.boxMapper.selectByPrimaryKey(order.getBoxId());
            box.setStatus(BoxStatusEnum.FREE.getCode());
            this.boxMapper.updateByPrimaryKey(box);
            mqttMessageService.send(String.valueOf(box.getId()));
        });
        return true;
    }

    @Override
    public List<OrderDTO> getOrdersByIds(List<Integer> orderIds) {
        return this.orderMapper.selectOrdersByIds(orderIds);
    }

    private synchronized Box determineBox(String address) throws BusinessException {
        List<Box> freeBoxList = boxMapper.selectByStatusAndAddress(Box.builder().address(address).status(BoxStatusEnum.FREE.getCode()).build());
        if (CollectionUtils.isEmpty(freeBoxList)) {
            throw new BusinessException(ErrorResponseEnum.BOX_NOT_ENOUGH);
        }
        Box box = freeBoxList.get(FIRST);
        box.setStatus(BoxStatusEnum.RESERVED.getCode());
        boxMapper.updateByPrimaryKey(box);
        return box;
    }

    private List<OrderItemDTO> generateOrderItemDTO(List<Cart> cartList) {
        List<OrderItemDTO> result = new ArrayList<>();
        cartList.forEach(item -> {
            Integer productId = item.getProductId();
            Product product = productMapper.selectByPrimaryKey(productId);
            result.add(OrderItemDTO.builder().quantity(item.getQuantity()).product(ProductDTO.of(product)).build());
        });
        return result;
    }

    private double getOrderTotalPrice(List<OrderItemDTO> orderItemDTOS) {
        double totalPrice = 0.00;
        for (OrderItemDTO orderItemDTO : orderItemDTOS) {
            totalPrice = totalPrice + orderItemDTO.getQuantity() * orderItemDTO.getProduct().getPrice();
        }
        return totalPrice;
    }

    private void saveOrderItem(List<Cart> cartList, String orderNumber) {
        cartList.forEach(item -> {
            OrderItem orderItem = OrderItem.builder()
                    .orderNumber(orderNumber)
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            orderItemMapper.insert(orderItem);
        });
    }

    private synchronized void updateStockAndSale(List<Cart> cartList) throws BusinessException {
        // 第一步： 查询
        List<Integer> productIdList = cartList.stream().map(Cart::getProductId).collect(Collectors.toList());

        // 第二步：判断
        List<Product> products = productMapper.selectAllByProductIds(productIdList);

        List<Product> needUpdateProducts = new ArrayList<>();
        for (Product product : products) {
            Integer stock = product.getStock();
            Integer quantity = cartList.stream().filter(item -> product.getId().equals(item.getProductId())).collect(Collectors.toList()).get(FIRST).getQuantity();
            if (quantity > stock) {
                String errorMessage = String.format("商品[%s]库存不足", product.getName());
                throw new BusinessException(ErrorResponseEnum.STOCK_NOT_ENOUGH, errorMessage);
            } else {
                Product current = new Product();
                BeanUtils.copyProperties(product, current);
                current.setStock(stock - quantity);
                current.setSales(product.getSales() + quantity);
                needUpdateProducts.add(current);
            }
        }

        // 第三步：修改
        needUpdateProducts.forEach(item -> {
            productMapper.update(item);
        });
    }

    private void clearCart(List<Cart> cartList) {
        cartList.forEach(item -> {
            cartMapper.deleteByPrimaryKey(item.getId());
        });
    }

    @Override
    public PageResult getShopOrders(Integer shopId, int page, int pageSize, List<Integer> statusList) {
        List<String> shopOrderNumbers = orderMapper.getShopOrderNumber(shopId, statusList);
        int total = shopOrderNumbers.size();
        List<OrderDTO> orders = convertOrderDTO(orderMapper.getOrdersByShop(shopOrderNumbers, (page - 1) * pageSize, pageSize));
        int totalPage = total / pageSize;
        if (total < pageSize && total != 0) {
            totalPage = 1;
        }
        return PageResult.builder().list(orders).total(total).totalPage(totalPage).build();
    }

    private List<OrderDTO> convertOrderDTO(List<Order> orderList) {
        return orderList.stream().map(n -> {
            List<OrderItemDTO> orderItemDTOList = orderItemMapper.selectAllByOrderNumber(n.getOrderNumber()).stream().map(o -> {
                ProductDTO productDTO = ProductDTO.of(productMapper.selectByPrimaryKey(o.getProductId()));
                return OrderItemDTO.of(o, productDTO);
            }).collect(Collectors.toList());
            Box box = boxMapper.selectByPrimaryKey(n.getBoxId());
            BoxDTO boxDTO = Objects.nonNull(box) ? BoxDTO.of(box) : new BoxDTO();
            return OrderDTO.of(n, boxDTO, orderItemDTOList);
        }).collect(Collectors.toList());
    }

    @Override
    public boolean updateStatusByOrderNumber(String orderNumber, Integer status) {
        return orderMapper.updateStatusByOrderNumber(orderNumber, status) != 0;
    }

    @Override
    public int updateOrderStatus(Integer orderId, Order order) throws BusinessException {
        Order originalOrder = orderMapper.selectByPrimaryKey(orderId);
        if (Objects.isNull(originalOrder)) {
            throw new BusinessException(ErrorResponseEnum.ORDER_NOT_EXIST);
        }
        List<Integer> orderStatusCodes = Arrays.stream(OrderStatusEnum.values()).map(OrderStatusEnum::getCode).collect(Collectors.toList());
        if (!orderStatusCodes.contains(order.getStatus())) {
            throw new BusinessException(ErrorResponseEnum.ORDER_STATUS_INCORRECT);
        }
        originalOrder.setStatus(order.getStatus());
        if (order.getStatus() == 2) {
            originalOrder.setAcceptedTime(LocalDateTime.now());
        }
        if (order.getStatus() == 3) {
            originalOrder.setDeliverTime(LocalDateTime.now());
        }
        if (order.getStatus() == 6) {
            originalOrder.setCancelTime(LocalDateTime.now());
        }
        return orderMapper.updateByPrimaryKey(originalOrder);
    }

    @Override
    public OrderDTO getRecentOrderByUserId(Integer userId) {
        return orderMapper.selectRecentOrderByUserId(userId);
    }
}
