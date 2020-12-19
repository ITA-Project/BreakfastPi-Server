package com.ita.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Shop {
    private Integer id;

    private String name;

    private String description;

    private String address;

    private String phone;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}