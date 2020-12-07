package com.ita.domain.service;

import com.ita.domain.dto.CartDTO;
import com.ita.domain.vo.CartVO;

import java.util.List;

public interface CartService {
    void save(List<CartVO> cartVOList);
    List<CartDTO> getCartByUserId(Integer userId);
}
