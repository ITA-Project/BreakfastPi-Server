package com.ita.domain.service.impl;

import com.ita.domain.entity.Category;
import com.ita.domain.mapper.CategoryMapper;
import com.ita.domain.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public int create(Integer shopId, Category category) {
        return categoryMapper.createByShopId(shopId, category);
    }
}
