package com.ita.domain.controller;

import com.ita.domain.dto.CartDTO;
import com.ita.domain.service.CartService;
import com.ita.domain.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody List<CartVO> cartVOList) {
        cartService.save(cartVOList);
        return ResponseEntity.ok(true);
    }
    @GetMapping
    public ResponseEntity<List<CartDTO>> getCartByCurrentUser(Integer userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

}
