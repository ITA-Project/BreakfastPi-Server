package com.ita.domain.service;

import com.ita.domain.dto.PayInfoDTO;
import com.ita.domain.error.BusinessException;

/**
 * @author Dillon Xie
 * @date 12/22/2020
 */
public interface PayInfoService {
    PayInfoDTO createPayInfo(String orderNumber, Integer userId) throws BusinessException;
}
