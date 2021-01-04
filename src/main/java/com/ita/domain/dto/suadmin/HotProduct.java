package com.ita.domain.dto.suadmin;

import com.ita.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotProduct {
  private List<String> foodName;
  private List<Long> data;

  public static HotProduct from(List<Product> products, List<Long> sortedData) {
    return HotProduct.builder()
            .foodName(products.stream().map(Product::getName).collect(Collectors.toList()))
            .data(sortedData)
            .build();
  }
}
