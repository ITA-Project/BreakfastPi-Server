package com.ita.domain.controller;

import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.OrderDTO;
import com.ita.domain.dto.ProductDTO;
import com.ita.domain.service.impl.OrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
