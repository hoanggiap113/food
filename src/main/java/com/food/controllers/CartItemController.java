package com.food.controllers;

import com.food.dto.CartItemDTO;
import com.food.model.context.CartContext;
import com.food.model.entities.CartItem;
import com.food.services.JwtService;
import com.food.services.ICartItemService;
import com.food.services.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart_items")
@RequiredArgsConstructor
@Slf4j
public class CartItemController {

    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final JwtService jwtService;

    private CartContext extractCartContext(String bearerToken, UUID sessionId) {
        Long userId = null;
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            try {
                String token = bearerToken.substring(7);
                userId = Long.parseLong(jwtService.extractUserId(token));
            } catch (Exception e) {
                log.warn("JWT validation failed: {}", e.getMessage()); // log nguyên nhân
            }
        }
        return new CartContext(userId, sessionId);
    }

    @PostMapping
    public ResponseEntity<?> addCartItem(
            @Valid @RequestBody CartItemDTO cartItemDTO,
            BindingResult bindingResult,
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestHeader(value = "Session-Id", required = false) UUID sessionId
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        CartContext context = extractCartContext(bearerToken, sessionId);
        // Lấy hoặc tạo cart
        Long cartId = cartService.getOrCreateCart(context).getId();
        cartItemDTO.setCartId(cartId);

        CartItem added = cartItemService.saveCartItem(cartItemDTO);
        return ResponseEntity.ok(added);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("id") Long id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok("Xóa sản phẩm khỏi giỏ hàng thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartItem(@PathVariable("id") Long id) {
        try {
            CartItem cartItem = cartItemService.getCartItem(id);
            return ResponseEntity.ok(cartItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
