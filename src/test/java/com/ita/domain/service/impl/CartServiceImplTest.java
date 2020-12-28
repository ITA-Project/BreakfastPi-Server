package com.ita.domain.service.impl;

import com.ita.domain.dto.CartDTO;
import com.ita.domain.entity.Cart;
import com.ita.domain.entity.Product;
import com.ita.domain.mapper.CartMapper;
import com.ita.domain.mapper.ProductMapper;
import com.ita.domain.vo.CartVO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class CartServiceImplTest {
    @Mock
    private CartMapper cartMapper;
    @Mock
    private ProductMapper productMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_save_success_when_save_cart_given_product_id_and_quantity() throws Exception {
        //given
        List<CartVO> cartVOList = Collections.singletonList(buildCartVO());
        //when
        getCartService().save(cartVOList);
        //then
        verify(cartMapper, times(1)).deleteByUserId(1);
        verify(cartMapper, times(1)).insert(any());
    }

    @Test
    public void should_return_cart_info_when_get_cart_given_user_id() throws Exception {
        //given
        Integer userId = 1;
        //mock
        when(cartMapper.selectByUserId(anyInt())).thenReturn(Collections.singletonList(buildCart()));
        when(productMapper.selectByPrimaryKey(anyInt())).thenReturn(buildProduct());
        //when
        List<CartDTO> cartDTOList = getCartService().getCartByUserId(userId);
        //then
        assertEquals(1, cartDTOList.size());
        assertEquals(Integer.valueOf(1), cartDTOList.get(0).getQuantity());
        assertEquals(Integer.valueOf(1), cartDTOList.get(0).getProduct().getCategoryId());
        assertEquals(Integer.valueOf(1), cartDTOList.get(0).getProduct().getSales());
        assertEquals(true, cartDTOList.get(0).getProduct().getStatus());
        assertEquals(Integer.valueOf(1), cartDTOList.get(0).getProduct().getStock());
        assertEquals("test product info", cartDTOList.get(0).getProduct().getDescription());
        assertEquals("test product", cartDTOList.get(0).getProduct().getName());
        assertEquals("http://www.baidu.com/tddpdf.png", cartDTOList.get(0).getProduct().getImageUrl());
        assertEquals(Double.valueOf(11.0), cartDTOList.get(0).getProduct().getPrice());

    }

    private CartServiceImpl getCartService() {
        CartServiceImpl cartService = new CartServiceImpl();
        cartService.setCartMapper(cartMapper);
        cartService.setProductMapper(productMapper);
        return cartService;
    }

    private CartVO buildCartVO() {
        return CartVO.builder()
                .userId(1)
                .productId(1)
                .quantity(1)
                .build();
    }

    private Cart buildCart() {
        return Cart.builder()
                .userId(1)
                .quantity(1)
                .productId(1)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .shopId(1)
                .build();
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
