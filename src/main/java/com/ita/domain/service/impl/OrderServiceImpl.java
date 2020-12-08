package com.ita.domain.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.OrderDTO;
import com.ita.domain.entity.Order;
import com.ita.domain.mapper.OrderMapper;
import com.ita.domain.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
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
}
