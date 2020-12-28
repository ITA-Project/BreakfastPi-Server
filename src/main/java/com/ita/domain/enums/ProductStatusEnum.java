package com.ita.domain.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {

    PENDING(0, "待审核"),
    PASS(1, "审核通过"),
    FAIL(-1, "审核不通过"),
    INACTVIE(-2, "已下架");

    private final Integer code;
    private final String value;

    ProductStatusEnum(Integer code, String value) {
        this.value = value;
        this.code = code;
    }
}
