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
public class OrderDO {
    private String id; //订单号
    private Integer userId;
    private Double totalPrice;
    private Integer status; //订单状态: 0-已取消(取消的订单还是可以展示给用户) 1-已下单  3-已发货 4-已装柜 5-交易成功（已领取） 6-已删除(删除后将不再展示给用户)
    private LocalDateTime orderTime; //订单确认时间（代表已经支付了）
    private LocalDateTime sendTime; //发货时间
    private LocalDateTime storeTime; //装柜时间
    private LocalDateTime endTime; //交易完成成功
    private LocalDateTime closeTime; //交易关闭时间
    @CreatedDate
    private LocalDateTime createTime; //订单生成时间
    @LastModifiedDate
    private LocalDateTime updateTime;
}
