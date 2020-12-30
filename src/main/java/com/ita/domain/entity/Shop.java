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
public class Shop {
    private Integer id;

    private String name;

    private String description;

    private String address;

    private String phone;

    private Integer userId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}