package com.ita.domain.service.impl;

import com.ita.domain.dto.OrderSaleDTO;
import com.ita.domain.dto.suadmin.HotProduct;
import com.ita.domain.dto.suadmin.OrderTime;
import com.ita.domain.dto.suadmin.SaleData;
import com.ita.domain.dto.suadmin.SaleDataDTO;
import com.ita.domain.dto.suadmin.query.OrderQuery;
import com.ita.domain.entity.Order;
import com.ita.domain.enums.FormatTimeTypeEnum;
import com.ita.domain.mapper.OrderMapper;
import com.ita.domain.mapper.ProductMapper;
import com.ita.domain.service.StatisticsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.util.Collections.EMPTY_LIST;

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
    return SaleDataDTO.builder().hot(generateHotProductStatistics(shopId, type))
            .orderTime(generateOrderTimeStatistics(shopId, type))
            .sale(generateSaleDataStatistics(shopId, type)).build();
  }

  private HotProduct generateHotProductStatistics(Integer shopId, String type) {
    Map<Integer, Long> data = new HashMap<>();
    productMapper.selectAll().forEach(
        product ->
                data.put(product.getId(), orderMapper.selectOrdersByProductIdAndShopAndPeriodTime(OrderQuery.from(product.getId(), shopId, type)))
    );

    Map<Integer, Long> sortedMap = new HashMap<>();
    data.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEachOrdered(s -> sortedMap.put(s.getKey(), s.getValue()));
    return HotProduct.from(sortedMap.keySet().stream().map(id -> productMapper.selectByPrimaryKey(id)).collect(Collectors.toList()), sortedMap);
  }

  private SaleData generateSaleDataStatistics(Integer shopId, String type) {
    List<OrderSaleDTO> orderSaleDTOList = orderMapper.selectSaleByShopAndPeriodTime(OrderQuery.from(shopId, type));
    if (CollectionUtils.isEmpty(orderSaleDTOList)) {
      return SaleData.builder().moneyData(EMPTY_LIST).orderData(EMPTY_LIST).build();
    }
    Integer count = FormatTimeTypeEnum.getByValue(type).getCount();
    List<Long> orderData = new ArrayList<>();
    List<Double> moneyData = new ArrayList<>();
    for (int i = 1; i < count + 1; i++) {
      int number = i;
      OrderSaleDTO orderSaleDTO = orderSaleDTOList.stream().filter(n -> n.getType() == number).findFirst().orElse(null);
      if (Objects.isNull(orderSaleDTO)) {
        orderData.add(0L);
        moneyData.add((double) 0);
      } else {
        orderData.add(orderSaleDTO.getSaleCount());
        BigDecimal bigDecimal = BigDecimal.valueOf(orderSaleDTO.getSaleAmount());
        moneyData.add(bigDecimal.setScale(2, ROUND_HALF_UP).doubleValue());
      }
    }
    return SaleData.builder().orderData(orderData).moneyData(moneyData).build();
  }

  private OrderTime generateOrderTimeStatistics(Integer shopId, String type) {
    List<Order> orders = orderMapper.selectOrderByShopAndPeriodTime(OrderQuery.from(shopId, type));
    LocalTime defaultStartTime = LocalTime.parse("07:30:00");
    List<Long> data = new ArrayList<>();
    data.add((long) getFirstPeriodTimeList(orders, defaultStartTime).size());
    data.add((long) getSecondPeriodTimeList(orders, defaultStartTime.plusMinutes(15)).size());
    data.add((long) getThirdPeriodTimeList(orders, defaultStartTime.plusMinutes(30)).size());
    data.add((long) getFourthPeriodTimeList(orders, defaultStartTime.plusMinutes(45)).size());
    data.add((long) getFifthPeriodTimeList(orders, defaultStartTime.plusHours(1)).size());
    return OrderTime.builder().data(data).build();
  }

  private List<Order> getFirstPeriodTimeList(List<Order> orders, LocalTime startTime) {
    return orders.stream()
            .filter(n -> n.getCompletedTime().toLocalTime().compareTo(startTime) > 0
                    && n.getCompletedTime().toLocalTime().compareTo(startTime.plusMinutes(15)) <= 0)
            .collect(Collectors.toList());
  }

  private List<Order> getSecondPeriodTimeList(List<Order> orders, LocalTime startTime) {
    return orders.stream()
            .filter(n -> n.getCompletedTime().toLocalTime().compareTo(startTime) > 0
                    && n.getCompletedTime().toLocalTime().compareTo(startTime.plusMinutes(15)) <= 0)
            .collect(Collectors.toList());
  }

  private List<Order> getThirdPeriodTimeList(List<Order> orders, LocalTime startTime) {
    return orders.stream()
            .filter(n -> n.getCompletedTime().toLocalTime().compareTo(startTime) > 0
                    && n.getCompletedTime().toLocalTime().compareTo(startTime.plusMinutes(15)) <= 0)
            .collect(Collectors.toList());
  }

  private List<Order> getFourthPeriodTimeList(List<Order> orders, LocalTime startTime) {
    return orders.stream()
            .filter(n -> n.getCompletedTime().toLocalTime().compareTo(startTime) > 0
                    && n.getCompletedTime().toLocalTime().compareTo(startTime.plusMinutes(15)) <= 0)
            .collect(Collectors.toList());
  }

  private List<Order> getFifthPeriodTimeList(List<Order> orders, LocalTime startTime) {
    return orders.stream()
            .filter(n -> n.getCompletedTime().toLocalTime().compareTo(startTime) > 0
                    && n.getCompletedTime().toLocalTime().compareTo(startTime.plusMinutes(15)) <= 0)
            .collect(Collectors.toList());
  }
}
