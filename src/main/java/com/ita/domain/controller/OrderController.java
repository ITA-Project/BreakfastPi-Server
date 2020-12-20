package com.ita.domain.controller;

import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.OrderDTO;
import com.ita.domain.dto.ProductDTO;
import com.ita.domain.error.BusinessException;
import com.ita.domain.service.impl.OrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<PageInfo<OrderDTO>> getUserOrders(@PathVariable Integer userId, @RequestParam int page, @RequestParam int pageSize, @RequestParam List<Integer> status) {
        return ResponseEntity.ok(orderService.getUserOrders(userId, page, pageSize, status));
    }

    @GetMapping("/users/{userId}/products")
    public ResponseEntity<List<ProductDTO>> getUserFavouriteProducts(@PathVariable Integer userId) {
        return ResponseEntity.ok(orderService.getUserFavouriteProducts(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestParam Integer userId,
                                                @RequestParam String address,
                                                @RequestParam LocalDateTime expectedMealTime) throws BusinessException {
        return ResponseEntity.ok(orderService.createOrder(userId, address, expectedMealTime));
    }


    @GetMapping("/detail")
    public ResponseEntity<OrderDTO> getOrderDetail(@RequestParam Integer userId, @RequestParam String orderNumber) throws BusinessException {
        return ResponseEntity.ok(orderService.getOrderDetail(orderNumber));
    }

    @GetMapping("/status")
    public ResponseEntity<PageInfo<OrderDTO>> getOrdersByStatus(@RequestParam List<Integer> status, @RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status, page, pageSize));
    }

    @PutMapping("/status/delivered")
    public ResponseEntity<Boolean> updateOrdersStatusToDelivered(@RequestParam List<Integer> orderIds){
        this.orderService.updateStatusByOrders(orderIds);
        return ResponseEntity.ok(true);
    }

}
