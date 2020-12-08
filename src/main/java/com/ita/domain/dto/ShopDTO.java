package com.ita.domain.dto;

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
}
