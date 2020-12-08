package com.ita.domain.controller;

import com.ita.domain.dto.ShopDTO;
import com.ita.domain.entity.Shop;
import com.ita.domain.service.ShopService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shops")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/details/{id}")
    public ShopDTO getShopDetailsByShopId(@PathVariable Integer id) {
        return shopService.assembleShopDTOByShopId(id);
    }

    @GetMapping("/{id}")
    public Shop getShopById(@PathVariable Integer id) {
        return shopService.selectById(id);
    }

    @GetMapping
    public List<Shop> getAllShop() {
        return shopService.selectAll();
    }

    @PostMapping
    public int create(@RequestBody Shop shop) {
        return shopService.create(shop);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Integer id) {
        return shopService.delete(id);
    }

    @PutMapping
    public int update(@RequestBody Shop shop) {
        return shopService.update(shop);
    }
}
