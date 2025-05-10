package com.food.controllers;

import com.food.model.entities.Cart;
import com.food.services.impl.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getCart(@Valid @PathVariable("id") Long id) {
        try{
            Cart existingCart = cartService.getCart(id);
            return ResponseEntity.ok(existingCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
