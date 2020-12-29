package com.ita.domain.service.impl;

import com.ita.domain.dto.suadmin.HotProduct;
import com.ita.domain.dto.suadmin.SaleDataDTO;
import com.ita.domain.dto.suadmin.query.OrderQuery;
import com.ita.domain.entity.Product;
import com.ita.domain.mapper.OrderMapper;
import com.ita.domain.mapper.ProductMapper;
import com.ita.domain.service.StatisticsService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

  private ProductMapper productMapper;

  private OrderMapper orderMapper;

  @Autowired
  public void setProductMapper(ProductMapper productMapper) {
    this.productMapper = productMapper;
  }

  @Autowired
  public void setOrderMapper(OrderMapper orderMapper) {
    this.orderMapper = orderMapper;
  }

  @Override
  public SaleDataDTO generateStatisticsData(Integer shopId, String type) {
    return SaleDataDTO.builder().hot(generateHotProductStatistics(shopId, type)).build();
  }

  private HotProduct generateHotProductStatistics(Integer shopId, String type) {
    List<Product> products = productMapper.selectAll();
    List<Long> data = new ArrayList<>();
    products.forEach(
        product ->
        {
          Long count = orderMapper.selectOrdersByProductIdAndShopAndPeriodTime(OrderQuery.from(product.getId(), shopId, type));
          data.add(count);
        }
    );
    return HotProduct.builder()
        .foodName(products.stream().map(Product::getName).collect(Collectors.toList()))
        .data(data)
        .build();
  }
}
