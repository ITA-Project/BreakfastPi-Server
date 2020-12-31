package com.ita.domain.scheduler;

import com.ita.domain.entity.Order;
import com.ita.domain.entity.OrderItem;
import com.ita.domain.entity.Product;
import com.ita.domain.enums.BoxStatusEnum;
import com.ita.domain.enums.OrderStatusEnum;
import com.ita.domain.mapper.BoxMapper;
import com.ita.domain.mapper.OrderItemMapper;
import com.ita.domain.mapper.OrderMapper;
import com.ita.domain.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dillon Xie
 * @date 12/30/2020
 */
@Slf4j
@Service
public class OrderMonitorScheduler {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private BoxMapper boxMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private OrderItemMapper orderItemMapper;

    @Scheduled(initialDelay = 6*1000, fixedRate = 2*1000)
    public void monitorNoPayOrder() {
        List<Order> noPayOrderList = orderMapper.selectAllByStatus(OrderStatusEnum.NO_PAY.getCode());
        if (CollectionUtils.isNotEmpty(noPayOrderList)) {
            for (Order order : noPayOrderList) {
                LocalDateTime createTime = order.getCreateTime();
                long toMinutes = Duration.between(createTime, LocalDateTime.now()).toMinutes();
                log.info("createTime is {}, now is {}, toMinutes is {}", createTime, LocalDateTime.now(), toMinutes);
                if (toMinutes >= 5) {
                    rollbackProductAndBoxInfo(order);
                    log.info("order[orderId = {}] is canceled", order.getId());
                }
            }
        }
    }

    /*
      1.更新柜子状态
      2.更新商品库存和销量
      3.更新订单状态
     */
    private void rollbackProductAndBoxInfo(Order order) {
        String orderNumber = order.getOrderNumber();
        Integer boxId = order.getBoxId();
        boxMapper.updateStatusById(boxId, BoxStatusEnum.FREE.getCode());

        List<OrderItem> orderItems = orderItemMapper.selectAllByOrderNumber(orderNumber);
        for (OrderItem orderItem : orderItems) {
            Integer productId = orderItem.getProductId();
            Product product = productMapper.selectByPrimaryKey(productId);
            product.setSales(product.getSales() - orderItem.getQuantity());
            product.setStock(product.getStock() + orderItem.getQuantity());
            productMapper.update(product);
        }

        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCancelTime(LocalDateTime.now());
        orderMapper.updateByPrimaryKey(order);
    }
}
