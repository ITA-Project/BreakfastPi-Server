package com.ita.domain.dto;

import com.ita.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Product product;
    private Integer quantity;
}
