package com.ita.domain.dto.suadmin.query;

import com.ita.domain.enums.TimeTypeEnum;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderQuery {
  private Integer productId;
  private Integer shopId;
  private LocalDateTime startTime;
  private LocalDateTime endTime;

  public static OrderQuery from(Integer productId, Integer shopId, String type) {
    return OrderQuery.builder()
        .shopId(shopId)
        .productId(productId)
        .startTime(LocalDateTime.now().minusDays(TimeTypeEnum.getByValue(type).getDateCount()))
        .endTime(LocalDateTime.now())
        .build();
  }

}
