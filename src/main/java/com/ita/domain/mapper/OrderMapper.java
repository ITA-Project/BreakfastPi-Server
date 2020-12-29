package com.ita.domain.mapper;

import com.ita.domain.dto.OrderDTO;
import com.ita.domain.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    Order selectByOrderNumber(String orderNumber);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    List<OrderDTO> getOrdersByUser(Integer userId, List<Integer> statusList);

    OrderDTO getOrderDetail(String orderNumber);

    List<OrderDTO> selectOrdersByStatus(@Param("statusList") List<Integer> statusList);

    int updateStatusByPrimaryKey(List<Integer> orderIds, Integer status, Integer prevStatus);

    List<OrderDTO> selectOrdersByIds(@Param("orderIds") List<Integer> orderIds);

    List<OrderDTO> getOrdersByShop(List<Integer> statusList, List<Integer> productIdList);
}
