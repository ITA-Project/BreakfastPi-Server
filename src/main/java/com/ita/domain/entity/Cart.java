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
public class Cart {
    private Integer id;

    private Integer quantity;

    private Integer productId;

    private Integer userId;

    private Integer shopId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}