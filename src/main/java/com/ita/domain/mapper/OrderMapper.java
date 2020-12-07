package com.ita.domain.mapper;

import com.ita.domain.entity.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    List<Order> getOrdersByUser(Integer userId, List<Integer> statusList);
}
