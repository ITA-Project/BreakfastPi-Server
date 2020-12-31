package com.ita.domain.service.impl;

import com.ita.domain.dto.OrderSaleDTO;
import com.ita.domain.dto.suadmin.*;
import com.ita.domain.dto.suadmin.query.OrderQuery;
import com.ita.domain.entity.Order;
import com.ita.domain.entity.Shop;
import com.ita.domain.enums.FormatTimeTypeEnum;
import com.ita.domain.error.BusinessException;
import com.ita.domain.mapper.OrderMapper;
import com.ita.domain.mapper.ProductMapper;
import com.ita.domain.mapper.ShopMapper;
import com.ita.domain.service.StatisticsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.ita.domain.error.ErrorResponseEnum.SHOP_NOT_EXIST;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.util.Collections.EMPTY_LIST;

@Service
public class StatisticsServiceImpl implements StatisticsService {

  private ProductMapper productMapper;

  private OrderMapper orderMapper;

  private ShopMapper shopMapper;

  @Autowired
  public void setProductMapper(ProductMapper productMapper) {
    this.productMapper = productMapper;
  }

  @Autowired
  public void setOrderMapper(OrderMapper orderMapper) {
    this.orderMapper = orderMapper;
  }

  @Autowired
  public void setShopMapper(ShopMapper shopMapper) {
    this.shopMapper = shopMapper;
  }

  @Override
  public SaleDataDTO generateStatisticsData(Integer shopId, String type) throws BusinessException {
    Shop shop = shopMapper.selectByPrimaryKey(shopId);
    if (Objects.isNull(shop)) {
      throw new BusinessException(SHOP_NOT_EXIST);
    }
    return SaleDataDTO.builder().hot(generateHotProductStatistics(shopId, type))
            .orderTime(generateOrderTimeStatistics(shopId, type))
            .user(generateUserDataStatistics(shopId, type))
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
    Collections.reverse(orderData);
    Collections.reverse(moneyData);
    List<Long> orderDataResult = type.equals(FormatTimeTypeEnum.YEAR.getValue()) ? orderData.subList(0, 6) : orderData;
    List<Double> moneyResult = type.equals(FormatTimeTypeEnum.YEAR.getValue()) ? moneyData.subList(0, 6) : moneyData;
    return SaleData.builder().orderData(orderDataResult).moneyData(moneyResult).build();
  }

  private OrderTime generateOrderTimeStatistics(Integer shopId, String type) {
    List<Order> orders = orderMapper.selectOrderByShopAndPeriodTime(OrderQuery.from(shopId, type));
    LocalTime defaultStartTime = LocalTime.parse("07:30:00");
    List<Long> data = new ArrayList<>();
    data.add((long) getOrderByPeriodTime(orders, defaultStartTime, defaultStartTime.plusMinutes(15)).size());
    data.add((long) getOrderByPeriodTime(orders, defaultStartTime.plusMinutes(15), defaultStartTime.plusMinutes(30)).size());
    data.add((long) getOrderByPeriodTime(orders, defaultStartTime.plusMinutes(30), defaultStartTime.plusMinutes(45)).size());
    data.add((long) getOrderByPeriodTime(orders, defaultStartTime.plusMinutes(45), defaultStartTime.plusHours(1)).size());
    data.add((long) getOrderByPeriodTime(orders, defaultStartTime.plusHours(1), defaultStartTime.plusMinutes(75)).size());
    return OrderTime.builder().data(data).build();
  }

  private List<Order> getOrderByPeriodTime(List<Order> orders, LocalTime startTime, LocalTime endTime) {
    return orders.stream()
            .filter(n -> n.getCompletedTime().toLocalTime().compareTo(startTime) > 0
                    && n.getCompletedTime().toLocalTime().compareTo(endTime) <= 0)
            .collect(Collectors.toList());
  }

  private UserData generateUserDataStatistics(Integer shopId, String type) {
    Integer count = FormatTimeTypeEnum.getByValue(type).getCount();
    List<Long> userData = orderMapper.selectUserByShopAndPeriodTime(OrderQuery.from(shopId, type)).stream().map(UserDataDTO::getUserCount)
        .collect(Collectors.toList());
    while (userData.size() < count) {
      userData.add(0L);
    }
    List<Long> userDataResult = type.equals(FormatTimeTypeEnum.YEAR.getValue()) ? userData.subList(0, 6) : userData;
    return UserData.builder().data(userDataResult).build();
  }

}
