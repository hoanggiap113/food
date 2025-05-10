package com.food.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="cart_items")
public class CartItem extends AbstractEntity{
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.DETACH,CascadeType.REMOVE})
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name="quantity")
    private int quantity;

    @Column(name="price")
    private double price;
}
