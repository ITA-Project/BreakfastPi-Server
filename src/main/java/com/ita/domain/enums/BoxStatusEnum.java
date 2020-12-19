package com.ita.domain.enums;

import lombok.Getter;

/**
 * @author Dillon Xie
 * @date 12/19/2020
 */
@Getter
public enum BoxStatusEnum {

    FREE(0,"空闲"),
    RESERVED(1,"已预定"),
    LOADED(2, "已装货");

    private Integer code;
    private String value;

    BoxStatusEnum(Integer code, String value) {
        this.value = value;
        this.code = code;
    }
}
