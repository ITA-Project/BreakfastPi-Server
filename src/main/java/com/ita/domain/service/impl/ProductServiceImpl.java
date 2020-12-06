package com.ita.domain.service.impl;

import com.ita.domain.entity.Product;
import com.ita.domain.mapper.ProductMapper;
import com.ita.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductMapper productMapper;

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public Product selectById(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Product> selectAll() {
        return productMapper.selectAll();
    }

    @Override
    public int create(Product product) {
        return productMapper.insert(product);
    }

    @Override
    public int delete(Integer id) {
        return productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Product product) {
        return productMapper.updateByPrimaryKey(product);
    }
}
