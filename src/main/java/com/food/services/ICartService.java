package com.food.services;

import com.food.model.entities.Cart;
import org.springframework.stereotype.Service;

@Service
public interface ICartService {
    Cart getCart(Long cartId);
}
