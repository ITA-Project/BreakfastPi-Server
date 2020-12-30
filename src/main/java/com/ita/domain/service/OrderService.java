package com.ita.domain.service;

import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.OrderDTO;
import com.ita.domain.dto.ProductDTO;
import com.ita.domain.entity.Order;
import com.ita.domain.error.BusinessException;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    PageInfo<OrderDTO> getUserOrders(Integer userId, int page, int pageSize, List<Integer> statusList);

    List<ProductDTO> getUserFavouriteProducts(Integer userId);

    OrderDTO getOrderDetail(String orderNumber) throws BusinessException;

    OrderDTO createOrder(Integer userId, String address, LocalDateTime expectedMealTime) throws BusinessException;

    PageInfo<OrderDTO> getOrdersByStatus(List<Integer> statusList, int page, int pageSize);

    boolean updateStatusToDeliveredByOrders(List<Integer> orderIds);

    boolean updateStatusToCompletedByOrders(List<Integer> orderIds);

    int updateOrderStatus(Integer orderId, Order order) throws BusinessException;

    List<OrderDTO> getOrdersByIds(List<Integer> orderIds);

    PageInfo<OrderDTO> getShopOrders(Integer shop, int page, int pageSize, List<Integer> statusList);

    boolean updateStatusByOrderNumber(String orderNumber, Integer status);

    OrderDTO getRecentOrderByUserId(Integer userId);
}
