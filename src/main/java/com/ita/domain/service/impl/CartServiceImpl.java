package com.ita.domain.service.impl;

import com.ita.domain.dto.CartDTO;
import com.ita.domain.entity.Cart;
import com.ita.domain.mapper.CartMapper;
import com.ita.domain.mapper.ProductMapper;
import com.ita.domain.service.CartService;
import com.ita.domain.vo.CartVO;
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
    public void save(List<CartVO> cartVOList) {
        cartMapper.deleteByUserId(cartVOList.get(0).getUserId());
        cartVOList.forEach(cartVO -> cartMapper.insert(Cart.builder()
                .productId(cartVO.getProductId())
                .quantity(cartVO.getQuantity())
                .userId(cartVO.getUserId())
                .shopId(0)
                .build()));
    }

    @Override
    public List<CartDTO> getCartByUserId(Integer userId) {
        return cartMapper.selectByUserId(userId).stream().map(cart -> CartDTO.builder()
                .product(productMapper.selectByPrimaryKey(cart.getProductId()))
                .quantity(cart.getQuantity())
        .build()).collect(Collectors.toList());
    }
}
