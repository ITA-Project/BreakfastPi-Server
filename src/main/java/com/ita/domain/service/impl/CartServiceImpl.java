package com.ita.domain.service.impl;

import com.ita.domain.entity.Cart;
import com.ita.domain.mapper.CartMapper;
import com.ita.domain.mapper.ProductMapper;
import com.ita.domain.service.CartService;
import com.ita.domain.dto.user.CartDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements CartService{

    private CartMapper cartMapper;
    private ProductMapper productMapper;

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public void save(Integer userId, List<CartDTO> cartDTOList) {
        cartMapper.deleteByUserId(userId);
        cartDTOList.forEach(cartDTO -> cartMapper.insert(Cart.builder()
                .productId(cartDTO.getProductId())
                .quantity(cartDTO.getQuantity())
                .userId(cartDTO.getUserId())
                .shopId(cartDTO.getShopId())
                .build()));
    }

    @Override
    public List<com.ita.domain.dto.CartDTO> getCartByUserId(Integer userId) {
        return cartMapper.selectByUserId(userId).stream().map(cart -> com.ita.domain.dto.CartDTO.builder()
                .product(productMapper.selectByPrimaryKey(cart.getProductId()))
                .quantity(cart.getQuantity())
        .build()).collect(Collectors.toList());
    }
}
