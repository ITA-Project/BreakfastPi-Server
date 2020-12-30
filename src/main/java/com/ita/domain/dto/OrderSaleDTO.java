package com.ita.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderSaleDTO {

    private Integer type;

    private Long saleCount;

    private Double saleAmount;
}
