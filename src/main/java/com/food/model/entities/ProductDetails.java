package com.food.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="product_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name="price")
    private Double price;

    @Column(name="quantity")
    private Long quantity;

    @Column(name="status")
    private String status;

    @Column(name="is_apply")
    private Boolean isApply;
}
