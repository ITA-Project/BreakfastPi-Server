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
public class PayInfo {

    private Integer id;

    private String orderNumber;

    private String payNumber;

    private Integer payStatus;

    private Integer userId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}