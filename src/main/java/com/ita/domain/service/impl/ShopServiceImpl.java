package com.ita.domain.service.impl;

import com.ita.domain.dto.CategoryDTO;
import com.ita.domain.dto.ProductDTO;
import com.ita.domain.dto.ShopDTO;
import com.ita.domain.entity.Category;
import com.ita.domain.entity.Product;
import com.ita.domain.entity.Shop;
import com.ita.domain.enums.ProductStatusEnum;
import com.ita.domain.mapper.CategoryMapper;
import com.ita.domain.mapper.ProductMapper;
import com.ita.domain.mapper.ShopMapper;
import com.ita.domain.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    public ShopDTO assembleValidShopDTOByShopId(Integer shopId) {
        Shop shop = shopMapper.selectByPrimaryKey(shopId);
        List<Category> categories = categoryMapper.selectAllByShopId(shopId);
        List<Integer> categoryIds = categories.stream().map(Category::getId).collect(Collectors.toList());
        List<Product> products = productMapper.selectAllByCategoryIds(categoryIds);
        ShopDTO shopDTO = ShopDTO.of(shop, categories, products);
        filterValidProductsAndCategories(shopDTO);
        return shopDTO;
    }

    private void filterValidProductsAndCategories(ShopDTO shopDTO) {
        List<CategoryDTO> validCategories = shopDTO.getCategories().stream()
                .filter(category -> !CollectionUtils.isEmpty(category.getProducts()))
                .peek(category -> {
                    List<ProductDTO> validProducts = category.getProducts().stream()
                            .filter(product -> product.getStock() > 0 && ProductStatusEnum.PASS.getCode().equals(product.getStatus()))
                            .collect(Collectors.toList());
                    category.setProducts(validProducts);
                })
                .filter(category -> !CollectionUtils.isEmpty(category.getProducts()))
                .collect(Collectors.toList());
        shopDTO.setCategories(validCategories);
    }

    @Transactional(readOnly = true)
    @Override
    public ShopDTO assembleShopDTOByShopId(Integer shopId) {
        Shop shop = shopMapper.selectByPrimaryKey(shopId);
        List<Category> categories = categoryMapper.selectAllByShopId(shopId);
        List<Integer> categoryIds = categories.stream().map(Category::getId).collect(Collectors.toList());
        List<Product> products = productMapper.selectAllByCategoryIds(categoryIds);
        return ShopDTO.of(shop, categories, products);
    }

    @Override
    public Shop selectById(Integer id) {
        return shopMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Shop> selectAll() {
        return shopMapper.selectAll();
    }

    @Override
    public int create(Shop shop) {
        return shopMapper.insert(shop);
    }

    @Override
    public int delete(Integer id) {
        return shopMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Shop update(Shop shop) {
        shopMapper.updateByPrimaryKey(shop);
        return shopMapper.selectByPrimaryKey(shop.getId());
    }

    @Override
    public ShopDTO getShopByUserId(Integer userId) {
        return ShopDTO.of(shopMapper.selectByUserId(userId));
    }
}
