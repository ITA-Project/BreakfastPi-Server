package com.ita.domain.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.suadmin.ProductDTO;
import com.ita.domain.dto.suadmin.ProductStatusDTO;
import com.ita.domain.entity.Product;
import com.ita.domain.mapper.ProductMapper;
import com.ita.domain.service.ProductService;
import java.util.stream.Collectors;
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
        return productMapper.update(product);
    }

    @Override
    public PageInfo<Product> getRecommendProducts(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Product> products = productMapper.getRecommendProducts();
        return new PageInfo<>(products);
    }

    @Override
    public PageInfo<ProductDTO> getProductByStatus(Integer productStatus, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return new PageInfo<>(productMapper.selectByStatus(productStatus).stream().map(ProductDTO::from).collect(Collectors.toList()));
    }

    @Override
    public Boolean updateProductStatus(ProductStatusDTO productStatusDTO) {
        Product product = productMapper.selectByPrimaryKey(Integer.parseInt(productStatusDTO.getId()));
        Product.from(product, productStatusDTO);
        int updateResult = productMapper.update(product);
        return updateResult > 0;
    }
}
