package com.ita.domain.service;

import com.ita.domain.dto.ShopDTO;
import com.ita.domain.entity.Shop;

import java.util.List;

public interface ShopService {

    ShopDTO assembleValidShopDTOByShopId(Integer id);

    ShopDTO assembleShopDTOByShopId(Integer id);

    Shop selectById(Integer id);

    List<Shop> selectAll();

    int create(Shop shop);

    int delete(Integer id);

    Shop update(Shop shop);

    ShopDTO getShopByUserId(Integer userId);
}
