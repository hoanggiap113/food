package com.food.model.entities;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="categories")
public class Category extends AbstractEntity{

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "categoryId",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Product> products = new ArrayList<>();
}
