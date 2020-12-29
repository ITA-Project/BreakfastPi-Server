package com.ita.domain.service;

import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.suadmin.ProductDTO;
import com.ita.domain.entity.Product;

import com.ita.domain.enums.ProductStatusEnum;
import java.util.List;

public interface ProductService {

  Product selectById(Integer id);

  List<Product> selectAll();

  int create(Product product);

  int delete(Integer id);

  int update(Product product);

  PageInfo<Product> getRecommendProducts(int page, int pageSize);

  PageInfo<ProductDTO> getProductByStatus(Integer productStatus, int page, int pageSize);
}
