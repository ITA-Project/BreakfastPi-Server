package com.ita.domain.assembler;

import com.ita.domain.dto.CategoryDTO;
import com.ita.domain.entity.Category;

public class CategoryAssembler {

    public static CategoryDTO convertToDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .status(category.getStatus())
                .sequence(category.getSequence())
                .shopId(category.getShopId())
                .createTime(category.getCreateTime())
                .updateTime(category.getUpdateTime())
                .build();
    }
}
