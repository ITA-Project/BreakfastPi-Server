package com.ita.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    private Integer id;

    private String name;

    private Integer status;

    private Integer sequence;

    private Integer shopId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}