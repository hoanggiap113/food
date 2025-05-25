package com.food.services.impl;

import com.food.customexceptions.DataNotFoundException;
import com.food.request.CartItemDTO;
import com.food.model.entities.Cart;
import com.food.model.entities.CartItem;
import com.food.model.entities.Product;
import com.food.repositories.CartItemRepository;
import com.food.repositories.CartRepository;
import com.food.repositories.ProductRepository;
import com.food.services.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    @Override
    public CartItem getCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(() -> new DataNotFoundException("Cannot find cart item with id: " + cartItemId));
    }
    //Thêm sản phẩm vào giỏ hàng
    @Override
    public CartItem saveCartItem(CartItemDTO cartItemDTO) {
        Cart cart = cartRepository.findById(cartItemDTO.getCartId()).orElseThrow(() -> new DataNotFoundException("Cannot find cart with id: " + cartItemDTO.getCartId()));
        Product product = productRepository.findById(cartItemDTO.getProductId()).orElseThrow(() -> new DataNotFoundException("Cannot find product with id: " + cartItemDTO.getProductId()));
        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(cartItemDTO.getQuantity())
                .price(cartItemDTO.getPrice())
                .build();
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

}
