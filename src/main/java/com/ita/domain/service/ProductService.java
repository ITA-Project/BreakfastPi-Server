package com.ita.domain.service;

import com.ita.domain.entity.Product;

import java.util.List;

public interface ProductService {

    public Product selectById(Integer id);

    public List<Product> selectAll();

    public int create(Product product);

    public int delete(Integer id);

    public int update(Product product);
}
