package com.food.services.impl;

import com.food.customexceptions.DataNotFoundException;
import com.food.model.entities.Cart;
import com.food.repositories.CartRepository;
import com.food.services.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    @Override
    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new DataNotFoundException("Cannot find cart with id: " + cartId));
    }

}
