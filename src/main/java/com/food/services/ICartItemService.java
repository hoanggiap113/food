package com.food.services;

import com.food.model.context.CartContext;
import com.food.model.entities.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICartItemService {

    List<CartItem> getCartItems(CartContext context);

    CartItem saveCartItem(Long cartId, Long productId);

    void increaseQuantity(Long cartItemId, CartContext context);

    void decreaseQuantity(Long cartItemId, CartContext context);

    void deleteCartItem(Long cartItemId, CartContext context);
}
