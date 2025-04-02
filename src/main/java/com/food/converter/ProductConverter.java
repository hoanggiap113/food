package com.food.converter;
import com.food.model.entities.ProductEntity;
import com.food.model.response.ProductResponse;
import com.food.utils.UtilsMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ProductConverter {
    public ProductResponse convertToResponse(ProductEntity productEntity) {
        return ProductResponse.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .category_name(productEntity.getCategoryEntity().getName())
                .image_url(productEntity.getImageUrl())
                .description(productEntity.getDescription())
                .build();
    }
}
