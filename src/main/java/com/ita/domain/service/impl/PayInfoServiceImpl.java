package com.ita.domain.service.impl;

import com.ita.domain.dto.PayInfoDTO;
import com.ita.domain.entity.PayInfo;
import com.ita.domain.enums.PayInfoEnum;
import com.ita.domain.mapper.PayInfoMapper;
import com.ita.domain.service.PayInfoService;
import com.ita.utils.IdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Dillon Xie
 * @date 12/22/2020
 */
@Service
public class PayInfoServiceImpl implements PayInfoService {

    @Resource
    private PayInfoMapper payInfoMapper;
    @Resource
    private IdWorker idWorker;

    @Override
    public PayInfoDTO createPayInfo(String orderNumber, Integer userId) {
        PayInfo payInfo = PayInfo.builder()
                .orderNumber(orderNumber)
                .payNumber(idWorker.nextId() + "")
                .userId(userId)
                .payStatus(PayInfoEnum.SUCCESS.getCode())
                .build();
        payInfoMapper.insert(payInfo);
        return PayInfoDTO.of(payInfo);
    }
}
