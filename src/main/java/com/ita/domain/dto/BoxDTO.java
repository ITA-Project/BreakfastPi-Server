package com.ita.domain.dto;

import com.ita.domain.entity.Category;
import com.ita.domain.entity.Product;
import com.ita.domain.entity.Shop;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BoxDTO {

    private Integer id;

    private String address;

    private Integer number;

    private Integer status;
}
