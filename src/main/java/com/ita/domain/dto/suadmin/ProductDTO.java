package com.ita.domain.dto.suadmin;

import com.ita.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
  private String id;
  private String name;
  private String description;
  private String imageUrl;
  private String statusMessage;

  public static ProductDTO from(Product product) {
    return ProductDTO.builder()
        .id(String.valueOf(product.getId()))
        .name(product.getName())
        .description(product.getDescription())
        .imageUrl(product.getImageUrl())
        .statusMessage(product.getStatusMessage())
        .build();
  }
}
