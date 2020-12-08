package com.ita.domain.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.OrderDTO;
import com.ita.domain.dto.ProductDTO;
import com.ita.domain.entity.Order;
import com.ita.domain.entity.OrderItem;
import com.ita.domain.entity.Product;
import com.ita.domain.mapper.OrderItemMapper;
import com.ita.domain.mapper.OrderMapper;
import com.ita.domain.mapper.ProductMapper;
import com.ita.domain.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ProductMapper productMapper;

    public OrderServiceImpl(OrderMapper orderMapper, OrderItemMapper orderItemMapper, ProductMapper productMapper) {
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.productMapper = productMapper;
    }

    @Override
    public int delete(Integer id){
        return orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int create(Order order){
        return orderMapper.insert(order);
    }

    @Override
    public Order detail(Integer id){
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> getAll(){
        return orderMapper.selectAll();
    }

    @Override
    public int update(Order order){
        return orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public PageInfo<OrderDTO> getUserOrders(Integer userId, int page, int pageSize, List<Integer> statusList){
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
}
