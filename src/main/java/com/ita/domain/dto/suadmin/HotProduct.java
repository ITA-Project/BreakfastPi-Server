package com.ita.domain.dto.suadmin;

import com.ita.domain.entity.Product;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

  public static HotProduct from(List<Product> products, Map<Integer, Long> sortedMap) {
    return HotProduct.builder()
        .foodName(products.stream().map(Product::getName).collect(Collectors.toList()))
        .data(new ArrayList<>(sortedMap.values()))
        .build();
  }
}
