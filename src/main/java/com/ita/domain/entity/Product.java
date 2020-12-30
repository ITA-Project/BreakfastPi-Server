package com.ita.domain.entity;

import com.ita.domain.dto.suadmin.ProductStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  private Integer id;

  private String name;

  private String description;

  private String imageUrl;

  private Double price;

  private Integer stock;

  private Integer sales;

  private Integer status;

  private String statusMessage;

  private Integer categoryId;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;

  public static void from(Product product, ProductStatusDTO productStatusDTO) {
    product.setStatus(productStatusDTO.getStatus());
    product.setStatusMessage(productStatusDTO.getStatusMessage());
  }

}
