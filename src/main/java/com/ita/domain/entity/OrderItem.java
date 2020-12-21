package com.ita.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class OrderItem {
    private Integer id;

    private String orderNumber;

    private Integer quantity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer productId;
}
