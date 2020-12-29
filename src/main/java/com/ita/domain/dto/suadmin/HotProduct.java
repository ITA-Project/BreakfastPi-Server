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
public class HotProduct {
  private List<String> foodName;
  private List<Long> data;
}
