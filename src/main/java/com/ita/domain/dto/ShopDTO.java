package com.ita.domain.dto;

import com.ita.domain.entity.Shop;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ShopDTO {

    private Integer id;

    private String name;

    private String description;

    private String address;

    private String phone;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<CategoryDTO> categories;

    public static ShopDTO of(Shop shop) {
        return ShopDTO.builder()
                .id(shop.getId())
                .name(shop.getName())
                .description(shop.getDescription())
                .address(shop.getAddress())
                .phone(shop.getPhone())
                .createTime(shop.getCreateTime())
                .updateTime(shop.getUpdateTime())
                .build();
    }
}
