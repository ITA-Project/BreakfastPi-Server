package com.ita.domain.enums;

import lombok.Getter;

@Getter
public enum ShopStatusEnum {

  ACTIVE(0, "正常"),
  INACTIVE(1, "封禁");

  private final Integer code;
  private final String value;

  ShopStatusEnum(Integer code, String value) {
    this.value = value;
    this.code = code;
  }

}
