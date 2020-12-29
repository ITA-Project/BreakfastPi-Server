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
public class Category {
    private Integer id;

    private String name;

    private Boolean status;

    private Integer sequence;

    private Integer shopId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}