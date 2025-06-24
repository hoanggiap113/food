package com.food.services.impl;

import com.food.customexceptions.DataNotFoundException;
import com.food.model.context.CartContext;
import com.food.model.entities.Cart;
import com.food.model.entities.CartItem;
import com.food.model.entities.Product;
import com.food.model.entities.ProductInventory;
import com.food.repositories.CartItemRepository;
import com.food.repositories.CartRepository;
import com.food.repositories.ProductInventoryRepository;
import com.food.repositories.ProductRepository;
import com.food.services.ICartItemService;
import com.food.services.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ProductInventoryRepository productInventoryRepository;
    private final ICartService cartService;

    @Override
    public List<CartItem> getCartItems(CartContext context) {
        Cart cart = cartService.findOrCreateCart(context);
        return cartItemRepository.findByCart(cart);
    }

    @Override
    public CartItem saveCartItem(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find cart with id: " + cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id: " + productId));

        Optional<CartItem> existingItem = cartItemRepository.findByCart(cart).stream()
                .filter(i -> i.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Neu da ton tai thi thong bao cartItem da ton tai trong gio hang
            throw new IllegalStateException("Product already exists in cart");
        }

        ProductInventory inventory = productInventoryRepository.findCurrentByProductId(productId);
        if (inventory == null) {
            throw new DataNotFoundException("No active inventory found for product id: " + productId);
        }

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(1)
                .price(inventory.getPrice())
                .build();
            //CHỗ này, trên front- tôi truyền như này cơ
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void increaseQuantity(Long cartItemId, CartContext context) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new DataNotFoundException("Cart item not found with id: " + cartItemId));

        if (itemBelongsToContext(item, context)) {
            throw new SecurityException("You do not have permission to modify this cart item.");
        }

        item.setQuantity(item.getQuantity() + 1);
        cartItemRepository.save(item);
    }

    @Override
    public void decreaseQuantity(Long cartItemId, CartContext context) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new DataNotFoundException("Cart item not found with id: " + cartItemId));

        if (itemBelongsToContext(item, context)) {
            throw new SecurityException("You do not have permission to delete this cart item.");
        }

        if (item.getQuantity() <= 1) {
            cartItemRepository.deleteById(cartItemId);
        } else {
            item.setQuantity(item.getQuantity() - 1);
            cartItemRepository.save(item);
        }
    }

    @Override
    public void deleteCartItem(Long cartItemId, CartContext context) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new DataNotFoundException("Cart item not found with id: " + cartItemId));

        if (itemBelongsToContext(item, context)) {
            throw new SecurityException("You do not have permission to delete this cart item.");
        }

        cartItemRepository.deleteById(cartItemId);
    }

    private boolean itemBelongsToContext(CartItem item, CartContext context) {
        Long ownerId = item.getCart().getUser() != null ? item.getCart().getUser().getId() : null;
        String cartSessionId = item.getCart().getSessionId();

        boolean userMatches = context.getUserId().isPresent() && ownerId != null && context.getUserId().get().equals(ownerId);
        boolean sessionMatches = context.getSessionId().isPresent() && cartSessionId != null && context.getSessionId().get().equals(cartSessionId);

        return !userMatches && !sessionMatches;
    }
}
