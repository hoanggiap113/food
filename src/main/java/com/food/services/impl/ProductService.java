package com.food.services.impl;
import java.time.LocalDateTime;
import java.util.*;

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
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductResponse> getAll() {
        List<ProductResponse> results = productRepository.findAll().stream().map(ProductResponse::fromProduct).collect(Collectors.toList());
        return results;
    }

    @Override
    public ProductEntity getProductById(Long id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found with id=" + id));
    }

    @Override
    public ProductEntity saveProduct(ProductRequestDTO body) throws Exception {
        CategoryEntity existingCategory = categoryRepository.findById(body.getCategory_id()).orElseThrow(() -> new DataNotFoundException("Category not found with id=" + body.getCategory_id()));
        ProductEntity productEntity = productRepository.findByName(body.getName());
        if(body.getName() != null && !body.getName().isEmpty() && productEntity == null) {
            ProductEntity productNew = ProductEntity.builder()
                    .name(body.getName())
                    .description(body.getDescription())
                    .price(body.getPrice())
                    .imageUrl(body.getImage_url())
                    .quantity(body.getQuantity())
                    .status("available")
                    .type(body.getType().stream().collect(Collectors.joining(",")))
                    .categoryEntity(existingCategory)
                    .build();
            return productRepository.save(productNew);
        }else{
            return null;
        }
    }

    @Override
    public ProductEntity updateProduct(ProductRequestDTO body, Long id) throws Exception {
        ProductEntity existingProduct = getProductById(id);
        if(existingProduct != null) {
            CategoryEntity existingCategory = categoryRepository.findById(body.getCategory_id()).orElseThrow(() ->
                    new DataNotFoundException("Category not found with id=" + body.getCategory_id()));
            existingProduct.setName(body.getName());
            existingProduct.setDescription(body.getDescription());
            existingProduct.setPrice(body.getPrice());
            existingProduct.setImageUrl(body.getImage_url());
            existingProduct.setQuantity(body.getQuantity());
            existingProduct.setType(body.getType().stream().collect(Collectors.joining(",")));
            existingProduct.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) throws Exception {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        if(productEntity.isPresent()) {
            productRepository.delete(productEntity.get());
        }
    }

}
