package com.ita.domain.scheduler;

import com.ita.domain.entity.Order;
import com.ita.domain.enums.OrderStatusEnum;
import com.ita.domain.mapper.OrderMapper;
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

    @Scheduled(initialDelay = 6*1000, fixedRate = 2*1000)
    public void monitorNoPayOrder() {
        List<Order> noPayOrderList = orderMapper.selectAllByStatus(OrderStatusEnum.NO_PAY.getCode());
        if (CollectionUtils.isNotEmpty(noPayOrderList)) {
            for (Order order : noPayOrderList) {
                LocalDateTime createTime = order.getCreateTime();
                long toMinutes = Duration.between(createTime, LocalDateTime.now()).toMinutes();
                if (toMinutes >= 5) {
                    order.setStatus(OrderStatusEnum.CANCELED.getCode());
                    orderMapper.updateByPrimaryKey(order);
                    log.info("order[orderId = {}] is canceled", order.getId());
                }
            }
        }
    }
}
