package com.ita.domain.dto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductDTO {

    private Integer id;

    private String name;

    private String description;

    private String imageUrl;

    private Double price;

    private Integer stock;

    private Integer sales;

    private Integer status;

    private Integer categoryId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
