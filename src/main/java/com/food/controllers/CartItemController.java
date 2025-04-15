package com.food.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.food.dto.CartItemDTO;
import com.food.model.entities.CartItem;
import com.food.model.entities.Product;
import com.food.services.impl.CartItemService;
import com.food.services.impl.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart_items")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;
    private final ProductService productService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getCartItem(@Valid @PathVariable("id") Long id) {
        try{
            CartItem cartItem = cartItemService.getCartItem(id);
            return ResponseEntity.ok(cartItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("")
    public ResponseEntity<?> addCartItem(@Valid @RequestBody CartItemDTO cartItemDTO, BindingResult bindingResult) {
        try{
            if(bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage).collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorMessages);
            }
            CartItem cartItemResponse = cartItemService.saveCartItem(cartItemDTO);
            return ResponseEntity.ok(cartItemResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteCartItem(@Valid @PathVariable("id") Long id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok().body("Xoa san pham ra khoi gio hang thanh cong");
    }
}
