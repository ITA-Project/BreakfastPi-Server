package com.ita.domain.service;

import com.ita.domain.dto.user.CartDTO;

import java.util.List;

public interface CartService {
    void save(List<CartDTO> cartDTOList);
    List<com.ita.domain.dto.CartDTO> getCartByUserId(Integer userId);
}
