package com.food.services.impl;
import java.util.*;

import com.food.converter.ProductConverter;
import com.food.customexceptions.DataNotFoundException;
import com.food.model.entities.CategoryEntity;
import com.food.model.entities.ProductEntity;
import com.food.model.request.ProductRequestDTO;
import com.food.model.response.ProductResponse;
import com.food.repositories.CategoryRepository;
import com.food.repositories.ProductRepository;
import com.food.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.*;
import java.util.List;
@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductConverter productConverter;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductResponse> getAll() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream().map(productConverter::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public ProductEntity getProductById(Long id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found with id=" + id));
    }

    @Override
    public ProductEntity saveProduct(ProductRequestDTO body) throws Exception {
        CategoryEntity existingCategory = categoryRepository.findById(body.getCategory_id()).orElseThrow(() -> new DataNotFoundException("Category not found with id=" + body.getCategory_id()));

                ProductEntity productEntity = ProductEntity.builder()
                        .name(body.getName())
                        .description(body.getDescription())
                        .price(body.getPrice())
                        .imageUrl(body.getImage_url())
                        .quantity(body.getQuantity())
                        .status("available")
                        .type(body.getType().stream().collect(Collectors.joining(",")))
                        .categoryEntity(existingCategory)
                .build();
        return productRepository.save(productEntity);
    }
}
