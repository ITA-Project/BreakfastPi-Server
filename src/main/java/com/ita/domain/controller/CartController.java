package com.ita.domain.controller;

import com.ita.domain.service.CartService;
import com.ita.domain.dto.user.CartDTO;
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

    @PostMapping(path = "/{userId}")
    public ResponseEntity<Boolean> save(@PathVariable("userId") Integer userId, @RequestBody List<CartDTO> cartDTOList) {
        cartService.save(userId, cartDTOList);
        return ResponseEntity.ok(true);
    }

    @GetMapping
    public ResponseEntity<List<com.ita.domain.dto.CartDTO>> getCartByCurrentUser(Integer userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

}
