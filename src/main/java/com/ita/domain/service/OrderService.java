package com.ita.domain.service;

import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.OrderDTO;
import com.ita.domain.dto.ProductDTO;
import com.ita.domain.error.BusinessException;

import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderService {
    PageInfo<OrderDTO> getUserOrders(Integer userId, int page, int pageSize, List<Integer> statusList);

    List<ProductDTO> getUserFavouriteProducts(Integer userId);

    OrderDTO getOrderDetail(String orderNumber) throws BusinessException;

    OrderDTO createOrder(Integer userId, String address, LocalDateTime expectedMealTime) throws BusinessException;

    PageInfo<OrderDTO> getOrdersByStatus(List<Integer> statusList, int page, int pageSize);

    int updateStatusByOrders(List<Integer> orderIds);

    List<OrderDTO> getOrdersByIds(List<Integer> orderIds);
}
