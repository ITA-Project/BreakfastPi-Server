package com.ita.domain.dto.suadmin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatusDTO {
  private String id;
  private Integer status;
  private String statusMessage;
}
