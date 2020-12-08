package com.ita.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CategoryDTO {

    private Integer id;

    private String name;

    private Integer status;

    private Integer sequence;

    private Integer shopId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<ProductDTO> products;
}
