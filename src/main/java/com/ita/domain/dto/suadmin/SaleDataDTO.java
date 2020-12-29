package com.ita.domain.dto.suadmin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleDataDTO {
  private HotProduct hot;
  private OrderTime orderTime;
  private UserData user;
  private SaleData sale;
}
