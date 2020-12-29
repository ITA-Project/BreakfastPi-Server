package com.ita.domain.enums;

import java.util.Objects;
import lombok.Getter;

@Getter
public enum TimeTypeEnum {
  WEEK(0, "week", 7),
  MONTH(1, "month", 30),
  YEAR(2, "year", 365);

  private final Integer code;
  private final String value;
  private final Integer dateCount;

  TimeTypeEnum(Integer code, String value, Integer dateCount) {
    this.value = value;
    this.code = code;
    this.dateCount = dateCount;
  }

  public static TimeTypeEnum getByValue(String type) {
    for (TimeTypeEnum timeTypeEnum : values()) {
      if (Objects.equals(timeTypeEnum.value, type)) {
        return timeTypeEnum;
      }
    }
    throw new IllegalArgumentException(type);
  }
}
