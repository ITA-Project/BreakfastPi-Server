package com.ita.domain.controller;

import com.ita.domain.dto.suadmin.SaleDataDTO;
import com.ita.domain.error.BusinessException;
import com.ita.domain.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chart")
public class StatisticsController {

  private final StatisticsService statisticsService;

  public StatisticsController(StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  @GetMapping("/{shopId}")
  public ResponseEntity<SaleDataDTO> generateStatisticsData(@PathVariable Integer shopId, @RequestParam String type) throws BusinessException {
    return ResponseEntity.ok(statisticsService.generateStatisticsData(shopId, type));
  }
}
