package com.ita.domain.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDTO {
    private Integer id;

    private Integer quantity;

    private ProductDTO product;
}
