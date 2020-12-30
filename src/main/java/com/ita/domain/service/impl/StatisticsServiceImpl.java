package com.ita.domain.service.impl;

import com.ita.domain.dto.suadmin.HotProduct;
import com.ita.domain.dto.suadmin.SaleDataDTO;
import com.ita.domain.dto.suadmin.query.OrderQuery;
import com.ita.domain.entity.Product;
import com.ita.domain.mapper.OrderMapper;
import com.ita.domain.mapper.ProductMapper;
import com.ita.domain.service.StatisticsService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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
    Map<Integer, Long> data = new HashMap<>();
    productMapper.selectAll().forEach(
        product ->
            data.put(product.getId(), orderMapper.selectOrdersByProductIdAndShopAndPeriodTime(OrderQuery.from(product.getId(), shopId, type)))
    );

    Map<Integer, Long> sortedMap= new HashMap<>();
    data.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .forEachOrdered(s->sortedMap.put(s.getKey(),s.getValue()));
    return HotProduct.from(sortedMap.keySet().stream().map(id -> productMapper.selectByPrimaryKey(id)).collect(Collectors.toList()), sortedMap);
  }


}
