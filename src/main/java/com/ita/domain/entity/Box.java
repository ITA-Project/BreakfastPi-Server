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
public class Box {
    private Integer id;

    private String address;

    private Integer number;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}