package com.ita.domain.dto.suadmin.query;

import com.ita.domain.enums.FormatTimeTypeEnum;
import com.ita.domain.enums.TimeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderQuery {
  private Integer productId;
  private Integer shopId;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String formatType;

  public static OrderQuery from(Integer productId, Integer shopId, String type) {
    return OrderQuery.builder()
            .shopId(shopId)
            .productId(productId)
            .startTime(LocalDateTime.now().minusDays(TimeTypeEnum.getByValue(type).getDateCount()))
            .endTime(LocalDateTime.now())
            .build();
  }

  public static OrderQuery from(Integer shopId, String type) {
    return OrderQuery.builder()
            .shopId(shopId)
            .formatType(FormatTimeTypeEnum.getByValue(type).getFormatType())
            .startTime(LocalDateTime.now().minusDays(TimeTypeEnum.getByValue(type).getDateCount()))
            .endTime(LocalDateTime.now())
            .build();
  }

}
