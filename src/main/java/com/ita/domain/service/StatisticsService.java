package com.ita.domain.service;

import com.ita.domain.dto.suadmin.SaleDataDTO;

public interface StatisticsService {
  SaleDataDTO generateStatisticsData(Integer shopId, String type);
}
