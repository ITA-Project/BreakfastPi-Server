package com.ita.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;

    private String orderNumber;

    private LocalDateTime paymentTime;//支付时间

    private LocalDateTime deliverTime; // 发货时间

    private LocalDateTime storeTime; // 装箱时间

    private LocalDateTime acceptedTime; // 商家接单时间

    private LocalDateTime completedTime;

    private LocalDateTime cancelTime;

    private Double amount; // 订单总价

    private Integer userId;

    private Integer boxId;

    @CreatedDate
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    private LocalDateTime estimatedTime;// 用户期待收货时间

    private Integer status;
}
