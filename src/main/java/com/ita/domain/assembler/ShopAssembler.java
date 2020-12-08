package com.ita.domain.assembler;

import com.ita.domain.dto.CategoryDTO;
import com.ita.domain.dto.ProductDTO;
import com.ita.domain.dto.ShopDTO;
import com.ita.domain.entity.Category;
import com.ita.domain.entity.Product;
import com.ita.domain.entity.Shop;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopAssembler {

    public static ShopDTO convertToDTO(Shop shop) {
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

    public static ShopDTO assemblerToDTO(Shop shop, List<Category> categories, List<Product> products) {
        ShopDTO shopDTO = convertToDTO(shop);
        List<CategoryDTO> categoryDTOList = categories.stream().map(CategoryAssembler::convertToDTO).collect(Collectors.toList());
        List<ProductDTO> productDTOList = products.stream().map(ProductAssembler::convertToDTO).collect(Collectors.toList());
        return assemblerToDTO(shopDTO, categoryDTOList, productDTOList);
    }

    public static ShopDTO assemblerToDTO(ShopDTO shopDTO, List<CategoryDTO> categoryDTOList, List<ProductDTO> productDTOList) {
        Map<Integer, List<ProductDTO>> productMap = productDTOList.stream().collect(Collectors.groupingBy(ProductDTO::getCategoryId));
        categoryDTOList.forEach(categoryDTO -> categoryDTO.setProducts(productMap.get(categoryDTO.getId())));
        shopDTO.setCategories(categoryDTOList);
        return shopDTO;
    }
}
