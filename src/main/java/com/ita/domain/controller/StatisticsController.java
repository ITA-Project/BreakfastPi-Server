package com.ita.domain.controller;

import com.ita.domain.dto.suadmin.SaleDataDTO;
import com.ita.domain.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chart")
public class StatisticsController {

  private StatisticsService statisticsService;

  public StatisticsController(StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  @GetMapping("/{shopId}")
  public ResponseEntity<SaleDataDTO> generateStatisticsData(@PathVariable Integer shopId, @RequestParam String type) {
    return ResponseEntity.ok(statisticsService.generateStatisticsData(shopId, type));
  }
}
