package com.food.model.builders;

import com.food.model.entities.CategoryEntity;
import com.food.model.entities.ProductEntity;
import com.food.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

import java.time.LocalDateTime;
@Component
public class ProductBuilder {

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductEntity build(Map<String, Object> params) {
        // Lấy giá trị từ params
        String name = (String) params.get("name");
        String description = (String) params.get("description");
        Double price = params.get("price") != null ? Double.parseDouble(params.get("price").toString()) : null;
        String imageUrl = (String) params.get("image_url");
        Long categoryId = params.get("category_id") != null ? Long.parseLong(params.get("category_id").toString()) : null;

        // Kiểm tra các trường bắt buộc
        if (name == null || price == null || categoryId == null) {
            throw new IllegalArgumentException("Name, price, and category_id are required fields.");
        }

        // Tìm CategoryEntity từ categoryId
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));

        // Tạo và thiết lập giá trị cho ProductEntity
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(name);
        productEntity.setDescription(description);
        productEntity.setPrice(price);
        productEntity.setImageUrl(imageUrl);
        productEntity.setCreatedAt(LocalDateTime.now());
        productEntity.setUpdatedAt(LocalDateTime.now());
        productEntity.addCategory(categoryEntity);

        return productEntity;
    }
}
