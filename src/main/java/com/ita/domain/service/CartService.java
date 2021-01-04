package com.ita.domain.service;

import com.ita.domain.dto.user.CartDTO;

import java.util.List;

public interface CartService {
    void save(Integer userId, List<CartDTO> cartDTOList);
    List<com.ita.domain.dto.CartDTO> getCartByUserId(Integer userId);
}
