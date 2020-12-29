package com.ita.domain.dto.suadmin;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class SaleData {
  private List<Long> orderData;
  private List<Long> moneyData;
}
