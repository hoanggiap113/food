package com.food.repositories;

import com.food.model.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<ProductEntity,Long>{
    ProductEntity findByName(String name);
}
