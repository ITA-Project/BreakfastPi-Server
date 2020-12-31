package com.ita.domain.service;

import com.ita.domain.dto.suadmin.SaleDataDTO;
import com.ita.domain.error.BusinessException;

public interface StatisticsService {
  SaleDataDTO generateStatisticsData(Integer shopId, String type) throws BusinessException;
}
