package com.ita.domain.service.impl;

import com.ita.domain.assembler.ShopAssembler;
import com.ita.domain.dto.ShopDTO;
import com.ita.domain.entity.Category;
import com.ita.domain.entity.Product;
import com.ita.domain.entity.Shop;
import com.ita.domain.mapper.CategoryMapper;
import com.ita.domain.mapper.ProductMapper;
import com.ita.domain.mapper.ShopMapper;
import com.ita.domain.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class ShopServiceImpl implements ShopService {

    private final ShopMapper shopMapper;

    private final CategoryMapper categoryMapper;

    private final ProductMapper productMapper;

    public ShopServiceImpl(ProductMapper productMapper, CategoryMapper categoryMapper, ShopMapper shopMapper) {
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
        this.shopMapper = shopMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public ShopDTO assembleShopDTOByShopId(Integer shopId) {
        Shop shop = shopMapper.selectByPrimaryKey(shopId);
        List<Category> categories = categoryMapper.selectAllByShopId(shopId);
        List<Integer> categoryIds = categories.stream().map(Category::getId).collect(Collectors.toList());
        List<Product> products = productMapper.selectAllByCategoryIds(categoryIds);
        return ShopAssembler.assemblerToDTO(shop, categories, products);
    }
}
