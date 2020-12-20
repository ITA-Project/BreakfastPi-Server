package com.ita.domain.controller;

import com.ita.domain.entity.Category;
import com.ita.domain.service.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryServiceImpl;

    public CategoryController(CategoryService categoryServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
    }

    @PostMapping("/{shopId}")
    public int createByShopId(@PathVariable Integer shopId, @RequestBody Category category) {
        return categoryServiceImpl.create(shopId, category);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Integer id) {
        return categoryServiceImpl.delete(id);
    }
}
