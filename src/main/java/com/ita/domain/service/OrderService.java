package com.ita.domain.service;

import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.OrderDTO;
import com.ita.domain.entity.Order;

import java.util.List;

public interface OrderService {
    int delete(Integer id);

    int create(Order order);

    public Order detail(Integer id);

    public List<Order> getAll();

    public int update(Order order);

    public PageInfo<OrderDTO> getUserOrders(Integer userId, int page, int pageSize, List<Integer> statusList);
}
