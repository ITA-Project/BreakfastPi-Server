package com.ita.domain.service.impl;

import com.ita.domain.dto.PayInfoDTO;
import com.ita.domain.entity.Order;
import com.ita.domain.entity.PayInfo;
import com.ita.domain.enums.OrderStatusEnum;
import com.ita.domain.enums.PayInfoEnum;
import com.ita.domain.error.BusinessException;
import com.ita.domain.error.ErrorResponseEnum;
import com.ita.domain.mapper.OrderMapper;
import com.ita.domain.mapper.PayInfoMapper;
import com.ita.domain.service.PayInfoService;
import com.ita.utils.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Objects;

/**
 * @author Dillon Xie
 * @date 12/22/2020
 */
@Service
public class PayInfoServiceImpl implements PayInfoService {

    @Resource
    private PayInfoMapper payInfoMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private IdWorker idWorker;

    @Transactional
    @Override
    public PayInfoDTO createPayInfo(String orderNumber, Integer userId) throws BusinessException {
        Order order = orderMapper.selectByOrderNumber(orderNumber);
        if (Objects.isNull(order)) {
            throw new BusinessException(ErrorResponseEnum.ORDER_NUMBER_INCORRECT);
        }

        PayInfo payInfo = PayInfo.builder()
                .orderNumber(orderNumber)
                .payNumber(idWorker.nextId() + "")
                .userId(userId)
                .payStatus(PayInfoEnum.SUCCESS.getCode())
                .build();
        payInfoMapper.insert(payInfo);

        if (OrderStatusEnum.NO_PAY.getCode().equals(order.getStatus())) {
            orderMapper.updateStatusByPrimaryKey(Collections.singletonList(order.getId()), order.getStatus(), OrderStatusEnum.PAID.getCode());
        }
        return PayInfoDTO.of(payInfo);
    }
}
