package com.ita.domain.controller;

import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.suadmin.ProductDTO;
import com.ita.domain.dto.suadmin.ProductStatusDTO;
import com.ita.domain.entity.Product;
import com.ita.domain.service.ProductService;
import io.swagger.models.auth.In;
import org.springframework.http.ResponseEntity;
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
    public Product selectProductById(@PathVariable Integer id) {
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

    @GetMapping("/status/{status}")
    public ResponseEntity<PageInfo<ProductDTO>> getProductByStatus(@PathVariable Integer status,
        @RequestParam int page,
        @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(productService.getProductByStatus(status, page, pageSize));
    }

    @PutMapping("/status")
    public ResponseEntity<Boolean> updateProductStatus(@RequestBody ProductStatusDTO productStatusDTO) {
        return ResponseEntity.ok(productService.updateProductStatus(productStatusDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<List<com.ita.domain.dto.ProductDTO>> searchByName(@RequestParam String name, @RequestParam Integer shopId,
        @RequestParam Integer userId) {
        return ResponseEntity.ok(this.productService.searchProductByName(name, shopId, userId));
    }
}
