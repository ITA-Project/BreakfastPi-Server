package com.ita.domain.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {

  ACTIVE(0, "正常"),
  INACTIVE(1, "封禁");

  private final Integer code;
  private final String value;

  UserStatusEnum(Integer code, String value) {
    this.value = value;
    this.code = code;
  }

}
