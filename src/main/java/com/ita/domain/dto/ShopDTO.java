package com.ita.domain.dto;

import com.ita.domain.entity.Category;
import com.ita.domain.entity.Product;
import com.ita.domain.entity.Shop;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShopDTO {

    private Shop shop;

    private List<Category> categories;

    private List<Product> products;
}
