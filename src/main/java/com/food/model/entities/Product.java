package com.food.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="products")
public class Product extends AbstractEntity{

    @Column(name ="name",nullable = false,length = 300)
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="price",nullable = false)
    private Double price;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="quantity")
    private Long quantity;

    @Column(name="status")
    private String status;

    @Column(name="type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category categoryId;
}
