package com.ita.domain.enums;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum FormatTimeTypeEnum {
  WEEK(0, "week", "%e", 7),
  MONTH(1, "month", "%w", 4),
  YEAR(2, "year", "%m", 12);

  private final Integer code;
  private final String value;
  private final String formatType;
  private final Integer count;

  FormatTimeTypeEnum(Integer code, String value, String formatType, Integer count) {
    this.value = value;
    this.code = code;
    this.formatType = formatType;
    this.count = count;
  }

  public static FormatTimeTypeEnum getByValue(String type) {
    for (FormatTimeTypeEnum formatTimeTypeEnum : values()) {
      if (Objects.equals(formatTimeTypeEnum.value, type)) {
        return formatTimeTypeEnum;
      }
    }
    throw new IllegalArgumentException(type);
  }
}
