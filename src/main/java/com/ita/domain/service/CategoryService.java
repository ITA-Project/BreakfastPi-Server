package com.ita.domain.service;

import com.ita.domain.entity.Category;

public interface CategoryService {

    int create(Integer shopId, Category category);

    int delete(Integer id);

    int update(Category category);
}
