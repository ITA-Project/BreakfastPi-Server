package com.ita.domain.service.impl;

import com.ita.domain.entity.Category;
import com.ita.domain.mapper.CategoryMapper;
import com.ita.domain.mapper.ProductMapper;
import com.ita.domain.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper, ProductMapper productMapper) {
        this.categoryMapper = categoryMapper;
        this.productMapper = productMapper;
    }

    @Override
    public int create(Integer shopId, Category category) {
        return categoryMapper.createByShopId(shopId, category);
    }

    @Override
    public int delete(Integer id) {
        productMapper.deleteByCategoryId(id);
        return categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Category category) {
        return categoryMapper.updateByPrimaryKey(category);
    }
}
