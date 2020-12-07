package com.ita.domain.controller;

import com.github.pagehelper.PageInfo;
import com.ita.domain.entity.Order;
import com.ita.domain.service.impl.OrderServiceImpl;
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
    public PageInfo<Order> getUserOrders(@PathVariable Integer userId, @RequestParam int page, @RequestParam int pageSize, @RequestParam List<Integer> status) {
        return orderService.getUserOrders(userId, page, pageSize, status);
    }

}
