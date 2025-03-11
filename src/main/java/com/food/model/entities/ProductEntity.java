package com.food.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="name",nullable = false,length = 300)
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="price",nullable = false)
    private Double price;
    @Column(name="image_url")
    private String imageUrl;
    @Column(name="created_at")
    private Date createdAt;
    @Column(name="updated_at")
    private Date updatedAt;
    @ManyToOne
    @JoinColumn(name = "category_id")
    CategoryEntity CategoryEntity;
}
