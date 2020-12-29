package com.ita.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Integer id;

    private String orderNumber;

    private Integer quantity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer productId;
}
