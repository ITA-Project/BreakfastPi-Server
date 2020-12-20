package com.ita.domain.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Product {
    private Integer id;

    private String name;

    private String description;

    private String imageUrl;

    private Double price;

    private Integer stock;

    private Integer sales;

    private Boolean status;

    private Integer categoryId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}