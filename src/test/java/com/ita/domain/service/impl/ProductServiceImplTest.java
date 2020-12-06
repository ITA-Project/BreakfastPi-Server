package com.ita.domain.service.impl;

import com.ita.domain.entity.Product;
import com.ita.domain.mapper.ProductMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    @Mock
    private ProductMapper productMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_return_product_when_get_product_given_id() throws Exception {
        //given
        Integer productId = 1;
        //mock
        when(productMapper.selectByPrimaryKey(anyInt())).thenReturn(buildProduct());
        //when
        Product product = getProductService().selectById(1);
        //then
        assertEquals(Integer.valueOf(1), product.getCategoryId());
        assertEquals(Integer.valueOf(1), product.getSales());
        assertEquals(Integer.valueOf(1), product.getStatus());
        assertEquals(Integer.valueOf(1), product.getStock());
        assertEquals("test product info", product.getDescription());
        assertEquals("test product", product.getName());
        assertEquals("http://www.baidu.com/tddpdf.png", product.getImageUrl());
        assertEquals(Double.valueOf(11.0), product.getPrice());
    }

    @Test
    public void should_return_product_list_when_get_all_product() throws Exception {
        //mock
        when(productMapper.selectAll()).thenReturn(Collections.singletonList(buildProduct()));
        //when
        List<Product> productList = getProductService().selectAll();
        //then
        assertEquals(1, productList.size());
        assertEquals(Integer.valueOf(1), productList.get(0).getCategoryId());
        assertEquals(Integer.valueOf(1), productList.get(0).getSales());
        assertEquals(Integer.valueOf(1), productList.get(0).getStatus());
        assertEquals(Integer.valueOf(1), productList.get(0).getStock());
        assertEquals("test product info", productList.get(0).getDescription());
        assertEquals("test product", productList.get(0).getName());
        assertEquals("http://www.baidu.com/tddpdf.png", productList.get(0).getImageUrl());
        assertEquals(Double.valueOf(11.0), productList.get(0).getPrice());
    }

    @Test
    public void should_create_success_when_create_product_given_product_info() throws Exception {
        //given
        Product product = buildProduct();
        //mock
        when(productMapper.insert(any())).thenReturn(1);
        //when
        int result = getProductService().create(product);
        //then
        assertEquals(1, result);
    }

    @Test
    public void should_success_delete_when_delete_product_given_product_id() throws Exception {
        //given
        Integer id = 1;
        //mock
        when(productMapper.deleteByPrimaryKey(anyInt())).thenReturn(1);
        //when
        int result = getProductService().delete(id);
        //then
        assertEquals(1, result);
    }

    @Test
    public void should_update_success_when_update_product_given_product_info() throws Exception {
        //given
        Product product = buildProduct();
        //mock
        when(productMapper.updateByPrimaryKey(any())).thenReturn(1);
        //when
        int result = getProductService().update(product);
        //then
        assertEquals(1, result);
    }

    private ProductServiceImpl getProductService() {
        ProductServiceImpl productService = new ProductServiceImpl();
        productService.setProductMapper(productMapper);
        return productService;
    }

    private Product buildProduct() {
        return Product.builder()
                .categoryId(1)
                .name("test product")
                .imageUrl("http://www.baidu.com/tddpdf.png")
                .description("test product info")
                .price(11.0)
                .sales(1)
                .status(1)
                .categoryId(1)
                .stock(1)
                .build();
    }
}