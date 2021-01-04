package com.ita.domain.service;

import com.ita.domain.dto.OrderDTO;
import com.ita.domain.dto.ProductDTO;
import com.ita.domain.dto.common.PageResult;
import com.ita.domain.entity.Order;
import com.ita.domain.error.BusinessException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    PageResult getUserOrders(Integer userId, int page, int pageSize, List<Integer> statusList);

    List<ProductDTO> getUserFavouriteProducts(Integer userId);

    OrderDTO getOrderDetail(String orderNumber) throws BusinessException;

    OrderDTO createOrder(Integer userId, String address, LocalDateTime expectedMealTime) throws BusinessException;

    PageResult getOrdersByStatus(List<Integer> statusList, int page, int pageSize);

    boolean updateStatusToDeliveredByOrders(List<Integer> orderIds) throws Exception;

    boolean updateStatusToCompletedByOrders(List<Integer> orderIds);

    int updateOrderStatus(Integer orderId, Order order) throws BusinessException;

    List<OrderDTO> getOrdersByIds(List<Integer> orderIds);

    PageResult getShopOrders(Integer shop, int page, int pageSize, List<Integer> statusList);

    boolean updateStatusByOrderNumber(String orderNumber, Integer status, HttpServletRequest request);

    OrderDTO getRecentOrderByUserId(Integer userId);
}
