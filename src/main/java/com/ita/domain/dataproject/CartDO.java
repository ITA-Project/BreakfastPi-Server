package com.ita.domain.dataproject;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * @author Dillon Xie
 * @date 11/21/2020
 */
@Data
public class CartDO {
    private String id;
    private String foodId;
    private String userId;
    private String shopId; //商家id 参照美团饿了么外卖系统设计
    private String orderId; //订单号
    private Integer quantity;
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime updateTime;
}
