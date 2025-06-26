package com.food.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Getter
@Setter
@Table(name="products")

public class Product{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name ="name",nullable = false,length = 300)
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="created_at")
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductInventory> inventoryList;

}
