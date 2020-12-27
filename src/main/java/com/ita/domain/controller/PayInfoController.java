package com.ita.domain.controller;

import com.ita.domain.dto.PayInfoDTO;
import com.ita.domain.service.PayInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Dillon Xie
 * @date 12/22/2020
 */
@RestController
@RequestMapping("/payinfo")
public class PayInfoController {

    @Resource
    private PayInfoService payInfoService;

    @PostMapping("/create")
    public ResponseEntity<PayInfoDTO> createPayInfo(@RequestParam Integer userId, @RequestParam String orderNumber) {
        return ResponseEntity.ok(payInfoService.createPayInfo(orderNumber, userId));
    }
}
