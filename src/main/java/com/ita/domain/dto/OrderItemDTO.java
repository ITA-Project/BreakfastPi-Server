package com.ita.domain.dto;
import com.ita.domain.entity.OrderItem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDTO {
    private Integer id;

    private Integer quantity;

    private ProductDTO product;

    public static OrderItemDTO of(OrderItem orderItem, ProductDTO productDTO) {
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .product(productDTO)
                .build();
    }
}
