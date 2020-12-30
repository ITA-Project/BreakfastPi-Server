package com.ita.domain.dto;

import com.ita.domain.assembler.CategoryAssembler;
import com.ita.domain.assembler.ProductAssembler;
import com.ita.domain.entity.Category;
import com.ita.domain.entity.Product;
import com.ita.domain.entity.Shop;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        if (Objects.isNull(shop)) {
            return ShopDTO.builder().build();
        }
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

    public static ShopDTO of(Shop shop, List<Category> categories, List<Product> products) {
        ShopDTO shopDTO = ShopDTO.of(shop);
        List<CategoryDTO> categoryDTOList = categories.stream().map(CategoryAssembler::convertToDTO).collect(Collectors.toList());
        List<ProductDTO> productDTOList = products.stream().map(ProductAssembler::convertToDTO).collect(Collectors.toList());
        return of(shopDTO, categoryDTOList, productDTOList);
    }

    public static ShopDTO of(ShopDTO shopDTO, List<CategoryDTO> categoryDTOList, List<ProductDTO> productDTOList) {
        Map<Integer, List<ProductDTO>> productMap = productDTOList.stream().collect(Collectors.groupingBy(ProductDTO::getCategoryId));
        categoryDTOList.forEach(categoryDTO -> categoryDTO.setProducts(productMap.get(categoryDTO.getId())));
        shopDTO.setCategories(categoryDTOList);
        return shopDTO;
    }
}
