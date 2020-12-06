package com.ita.domain.controller;

import com.ita.domain.dto.ShopDTO;
import com.ita.domain.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/details/{shopId}")
    public ShopDTO getShopDetailsByShopId(@PathVariable Integer shopId) {
        return shopService.assembleShopDTOByShopId(shopId);
    }
}
