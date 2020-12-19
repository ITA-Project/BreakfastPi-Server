package com.ita.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order {
    private Integer id;

    private String orderNumber;

    private LocalDateTime paymentTime;

    private LocalDateTime deliverTime;

    private LocalDateTime storeTime;

    private LocalDateTime completedTime;

    private LocalDateTime cancelTime;

    private Double amount;

    private Integer userId;

    private Integer boxId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime estimatedTime;

    private Integer status;
}
