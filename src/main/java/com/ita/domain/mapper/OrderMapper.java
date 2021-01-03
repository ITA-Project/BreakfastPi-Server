package com.ita.domain.mapper;

import com.ita.domain.dto.OrderDTO;
import com.ita.domain.dto.OrderSaleDTO;
import com.ita.domain.dto.suadmin.UserDataDTO;
import com.ita.domain.dto.suadmin.query.OrderQuery;
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

    int updateStatusByOrderNumber(String orderNumber, Integer status);

    List<Order> getOrdersByUser(Integer userId, List<Integer> statusList, Integer start, Integer pageSize);

    int countUserOrders(Integer userId, List<Integer> statusList);

    int countOrdersByStatus(@Param("statusList") List<Integer> statusList);

    List<String> getShopOrderNumber(Integer shopId, List<Integer> statusList);

    OrderDTO getOrderDetail(String orderNumber);

    List<Order> selectOrdersByStatus(@Param("statusList") List<Integer> statusList, Integer start, Integer pageSize);

    int updateStatusByPrimaryKey(List<Integer> orderIds, Integer status, Integer prevStatus);

    List<OrderDTO> selectOrdersByIds(@Param("orderIds") List<Integer> orderIds);

    List<Order> getOrdersByShop(List<String> orderNumberList, Integer start, Integer pageSize);

    Long selectOrdersByProductIdAndShopAndPeriodTime(OrderQuery orderQuery);

    List<OrderSaleDTO> selectSaleByShopAndPeriodTime(OrderQuery orderQuery);

    List<UserDataDTO> selectUserByShopAndPeriodTime(OrderQuery orderQuery);

    List<Order> selectOrderByShopAndPeriodTime(OrderQuery orderQuery);

    List<Order> selectAllByStatus(Integer status);

    OrderDTO selectRecentOrderByUserId(Integer userId);
}
