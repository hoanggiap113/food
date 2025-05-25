package com.food.services;

import com.food.request.CartItemDTO;
import com.food.model.entities.CartItem;
import org.springframework.stereotype.Service;

@Service
public interface ICartItemService {
    CartItem getCartItem(Long cartItemId);
    CartItem saveCartItem(CartItemDTO cartItemDTO);
    void deleteCartItem(Long cartItemId);
}
