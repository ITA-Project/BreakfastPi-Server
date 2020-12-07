package com.ita.domain.controller;

import com.github.pagehelper.PageInfo;
import com.ita.domain.entity.Product;
import com.ita.domain.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Product selectBoxById(@PathVariable Integer id) {
        return productService.selectById(id);
    }

    @GetMapping
    public List<Product> selectAll() {
        return productService.selectAll();
    }

    @PostMapping
    public int create(@RequestBody Product product) {
        return productService.create(product);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Integer id) {
        return productService.delete(id);
    }

    @PutMapping
    public int update(@RequestBody Product product) {
        return productService.update(product);
    }

    @GetMapping("/recommend")
    public PageInfo<Product> getRecommendProducts(@RequestParam int page, @RequestParam int pageSize) {
        return productService.getRecommendProducts(page, pageSize);
    }
}
