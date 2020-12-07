package com.ita.domain.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ita.domain.entity.Order;
import com.ita.domain.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl {

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    int delete(Integer id){
        return orderMapper.deleteByPrimaryKey(id);
    }

    int create(Order order){
        return orderMapper.insert(order);
    }

    public Order detail(Integer id){
        return orderMapper.selectByPrimaryKey(id);
    }

    public List<Order> getAll(){
        return orderMapper.selectAll();
    }

    public int update(Order order){
        return orderMapper.updateByPrimaryKey(order);
    }

    public PageInfo<Order> getUserOrders(Integer userId, int page, int pageSize, List<Integer> statusList){
        PageHelper.startPage(page, pageSize);
        List<Order> orders = orderMapper.getOrdersByUser(userId, statusList);
        return new PageInfo<>(orders);
    }
}
