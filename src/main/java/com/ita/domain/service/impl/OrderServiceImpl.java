package com.ita.domain.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.BoxDTO;
import com.ita.domain.dto.OrderDTO;
import com.ita.domain.dto.OrderItemDTO;
import com.ita.domain.dto.ProductDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    @Override
    public PageInfo<OrderDTO> getUserOrders(Integer userId, int page, int pageSize, List<Integer> statusList) {
        PageHelper.startPage(page, pageSize);
        List<OrderDTO> orders = orderMapper.getOrdersByUser(userId, statusList);
        return new PageInfo<>(orders);
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
        OrderDTO orderDetail = orderMapper.getOrderDetail(orderNumber);
        if (Objects.isNull(orderDetail)) {
            throw new BusinessException(ErrorResponseEnum.UNKNOWN_ERROR);
        }
        return orderDetail;
    }


    @Transactional
    @Override
    public OrderDTO createOrder(Integer userId, String address, LocalDateTime expectedMealTime) throws BusinessException {
        /*
          1.获取用户购物车信息
          2.确定柜子号
          3.计算订单总价
          4.生成订单号
          5.创建订单
          6.更新订单项
          7.更新商品库存和销量
          8.清空购物车
         */

        List<Cart> cartList = cartMapper.selectByUserId(userId);
        if (CollectionUtils.isEmpty(cartList)) {
            throw new BusinessException(ErrorResponseEnum.CART_IS_EMPTY);
        }

        List<Box> freeBoxList = boxMapper.selectByStatusAndAddress(Box.builder().address(address).status(BoxStatusEnum.FREE.getCode()).build());
        if (CollectionUtils.isEmpty(freeBoxList)) {
            throw new BusinessException(ErrorResponseEnum.BOX_NOT_ENOUGH);
        }
        Box box = determineBox(freeBoxList);

        List<OrderItemDTO> orderItemDTOList = generateOrderItemDTO(cartList);

        double orderTotalPrice = getOrderTotalPrice(orderItemDTOList);

        Order order = Order.builder()
                .orderNumber(idWorker.nextId() + "")
                .amount(orderTotalPrice)
                .userId(userId)
                .boxId(box.getId())
                .estimatedTime(expectedMealTime)
                .status(OrderStatusEnum.PAID.getCode())
                .build();
        orderMapper.insert(order);

        saveOrderItem(cartList, order.getOrderNumber());

        updateStockAndSale(orderItemDTOList);

        clearCart(cartList);

        return OrderDTO.of(order, BoxDTO.of(box), orderItemDTOList);
    }

    @Override
    public PageInfo<OrderDTO> getOrdersByStatus(List<Integer> statusList, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<OrderDTO> orderDTOS = this.orderMapper.selectOrdersByStatus(statusList);
        return new PageInfo<>(orderDTOS);
    }

    @Override
    public int updateStatusByOrders(List<Integer> orderIds){
        return this.orderMapper.updateStatusByPrimaryKey(orderIds, OrderStatusEnum.DELIVERED.getCode(), OrderStatusEnum.SHIPPED.getCode());
    }

    @Override
    public List<OrderDTO> getOrdersByIds(List<Integer> orderIds){
        return this.orderMapper.selectOrdersByIds(orderIds);
    }

    // todo 后续可以优化逻辑
    private Box determineBox(List<Box> freeBoxList) {
        Box box = freeBoxList.get(FIRST);
        box.setStatus(BoxStatusEnum.RESERVED.getCode());
        box.setUpdateTime(LocalDateTime.now());
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

    private void updateStockAndSale(List<OrderItemDTO> orderItemDTOList) {
        orderItemDTOList.forEach(item -> {
            Product product = ProductDTO.toProduct(item.getProduct());
            product.setSales(product.getSales() + 1);
            product.setStock(product.getStock() - 1);
            productMapper.updateByPrimaryKey(product);
        });
    }

    private void clearCart(List<Cart> cartList) {
        cartList.forEach(item -> {
            cartMapper.deleteByPrimaryKey(item.getId());
        });
    }

    @Override
    public PageInfo<OrderDTO> getShopOrders(Integer shopId, int page, int pageSize, List<Integer> statusList) {
        List<Integer> categoryIdList = categoryMapper.selectAllByShopId(shopId)
                .stream().map(Category::getId).collect(Collectors.toList());
        List<Integer> productIdList = productMapper.selectAllByCategoryIds(categoryIdList)
                .stream().map(Product::getId).collect(Collectors.toList());
        PageHelper.startPage(page, pageSize);
        List<OrderDTO> orders = orderMapper.getOrdersByShop(statusList, productIdList);
        return new PageInfo<>(orders);
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
        return orderMapper.updateByPrimaryKey(originalOrder);
    }
}
