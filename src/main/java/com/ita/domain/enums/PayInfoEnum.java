package com.ita.domain.enums;

import lombok.Getter;

/**
 * @author Dillon Xie
 * @date 12/22/2020
 */
@Getter
public enum PayInfoEnum {
    SUCCESS(0, "支付成功"),
    FAILED(1, "支付失败");

    private Integer code;
    private String value;


    PayInfoEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
