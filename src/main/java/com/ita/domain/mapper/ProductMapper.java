package com.ita.domain.mapper;

import com.ita.domain.entity.Product;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    Product selectByPrimaryKey(Integer id);

    List<Product> selectAll();

    int update(Product record);

    List<Product> selectAllByCategoryIds(List<Integer> categoryIds);

    List<Product> getRecommendProducts();

    List<Product> selectAllByProductIds(List<Integer> productIds);

    int deleteByCategoryId(Integer categoryId);

    List<Product> selectByStatus(Integer status);
}
