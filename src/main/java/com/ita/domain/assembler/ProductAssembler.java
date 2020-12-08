package com.ita.domain.assembler;


import com.ita.domain.dto.ProductDTO;
import com.ita.domain.entity.Product;

public class ProductAssembler {

    public static ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .price(product.getPrice())
                .stock(product.getStock())
                .sales(product.getSales())
                .status(product.getStatus())
                .categoryId(product.getCategoryId())
                .createTime(product.getCreateTime())
                .updateTime(product.getUpdateTime())
                .build();
    }
}
