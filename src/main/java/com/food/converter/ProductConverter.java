package com.food.converter;
import com.food.model.entities.ProductEntity;
import com.food.model.response.ProductResponse;

import org.springframework.stereotype.Component;



@Component
public class ProductConverter {
    public ProductResponse convertToResponse(ProductEntity productEntity) {
        return ProductResponse.builder()
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .category_name(productEntity.getCategoryEntity().getName())
                .image_url(productEntity.getImageUrl())
                .description(productEntity.getDescription())
                .build();
    }
}
