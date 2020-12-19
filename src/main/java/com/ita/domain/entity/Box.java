package com.ita.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Box {
    private Integer id;

    private String address;

    private Integer number;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}