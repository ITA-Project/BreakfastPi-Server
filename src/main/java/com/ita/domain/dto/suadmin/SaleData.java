package com.ita.domain.dto.suadmin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleData {
  private List<Long> orderData;
  private List<Double> moneyData;
}
