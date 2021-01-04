package com.ita.domain.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.ShopDTO;
import com.ita.domain.dto.suadmin.ProductDTO;
import com.ita.domain.dto.suadmin.ProductStatusDTO;
import com.ita.domain.entity.Category;
import com.ita.domain.entity.Product;
import com.ita.domain.entity.Shop;
import com.ita.domain.entity.UserSearchHistory;
import com.ita.domain.mapper.CategoryMapper;
import com.ita.domain.mapper.ProductMapper;
import com.ita.domain.mapper.ShopMapper;
import com.ita.domain.mapper.UserSearchHistoryMapper;
import com.ita.domain.service.ProductService;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductMapper productMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private UserSearchHistoryMapper userSearchHistoryMapper;

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
        if (product == null) {
            return false;
        }
        Product.from(product, productStatusDTO);
        int updateResult = productMapper.update(product);
        return updateResult > 0;
    }

    @Override
    public ShopDTO searchProductByName(String searchKey, Integer shopId, Integer userId) {
        Shop shop = shopMapper.selectByPrimaryKey(shopId);
        List<Category> allCategories = categoryMapper.selectAllByShopId(shopId);
        List<Integer> categoryIds = allCategories.stream().map(Category::getId).collect(Collectors.toList());
        List<Product> products = getProductsBySearchKey(searchKey, categoryIds);
        UserSearchHistory userSearchHistory = UserSearchHistory
            .builder()
            .userId(userId)
            .shopId(shopId)
            .searchKey(searchKey)
            .build();
        userSearchHistoryMapper.insert(userSearchHistory);
        List<Category> categories = allCategories.stream().filter(
            category -> CollectionUtils.isNotEmpty(products.stream().filter(product -> product.getCategoryId().equals(category.getId())).collect(Collectors.toList())))
            .collect(
                Collectors.toList());
        ShopDTO shopDTO = ShopDTO.of(shop, categories, products);
        return shopDTO;
    }

    private List<Product> getProductsBySearchKey(String searchKey, List<Integer> categoryIds) {
        List<String> wordList = this.generateWordList(searchKey);
        List<Product> products;
        if(StringUtils.isEmpty(searchKey)) {
            products = this.productMapper.selectAllByCategoryIds(categoryIds);
        } else {
            products = this.productMapper.selectAllByCategoryIdsAndSearchKeyList(categoryIds, wordList);
        }
        return products;
    }

    private List<String> generateWordList(String searchKey) {
        String chineseRegEx = "[\\u4e00-\\u9fa5]";
        String charRegex = "[a-zA-Z]";
        String numberRegex = "[0-9]";
        List<String> keyList = new ArrayList<>();
        matchResult(Pattern.compile(chineseRegEx),searchKey, keyList);
        matchResult(Pattern.compile(charRegex),searchKey, keyList);
        matchResult(Pattern.compile(numberRegex),searchKey, keyList);
        return keyList;
    }

    private void matchResult(Pattern p, String str, List<String> keyList)
    {
        Matcher m = p.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++)
            {
                keyList.add(m.group());
            }
        }
    }
}
