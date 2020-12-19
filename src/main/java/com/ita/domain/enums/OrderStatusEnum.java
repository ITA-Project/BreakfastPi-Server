package com.ita.domain.enums;

import lombok.Getter;

/**
 * @author Dillon Xie
 * @date 12/19/2020
 */
@Getter
public enum OrderStatusEnum {

    NO_PAY(0,"未支付"),
    PAID(1,"已支付"),
    RECEIVE(2, "已接单"),
    SHIPPED(3, "已发货"),
    DELIVERED(4, "已送达"),
    COMPLETED(5,"已完成"),
    CANCELED(6,"已取消");

    private Integer code;
    private String value;

    OrderStatusEnum(Integer code, String value) {
        this.value = value;
        this.code = code;
    }
}
