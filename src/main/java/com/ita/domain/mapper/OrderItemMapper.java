package com.ita.domain.mapper;

import com.ita.domain.entity.OrderItem;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    List<OrderItem> selectAll();

    List<OrderItem> selectAllByOrderNumber(String orderNumber);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> getUserTopTreeOrderItem(Integer id);
}
