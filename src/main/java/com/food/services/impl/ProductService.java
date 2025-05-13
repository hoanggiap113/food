package com.food.services.impl;
import java.time.LocalDateTime;
import java.util.*;

import com.food.customexceptions.DataNotFoundException;
import com.food.model.entities.Category;
import com.food.model.entities.Product;
import com.food.dto.ProductRequestDTO;
import com.food.response.ProductResponseDTO;
import com.food.repositories.CategoryRepository;
import com.food.repositories.ProductRepository;
import com.food.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public List<ProductResponseDTO> getAll() {
        List<ProductResponseDTO> results = productRepository.findAll().stream().map(ProductResponseDTO::fromProduct).collect(Collectors.toList());
        return results;
    }

    @Override
    public Page<ProductResponseDTO> getAllProduct(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(ProductResponseDTO::fromProduct);
    }

    @Override
    public Product getProductById(Long id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found with id=" + id));
    }

    @Override
    public Product saveProduct(ProductRequestDTO body) throws Exception {
        Category existingCategory = categoryRepository.findById(body.getCategory_id()).orElseThrow(() -> new DataNotFoundException("Category not found with id=" + body.getCategory_id()));
        Product productEntity = productRepository.findByName(body.getName());
        if(body.getName() != null && !body.getName().isEmpty() && productEntity == null) {
            Product productNew = Product.builder()
                    .name(body.getName())
                    .description(body.getDescription())
                    .imageUrl(body.getImage_url())
                    .type(body.getType().stream().collect(Collectors.joining(",")))
                    .category(existingCategory)
                    .build();
            return productRepository.save(productNew);
        }else{
            return null;
        }
    }

    @Override
    public Product updateProduct(ProductRequestDTO body, Long id) throws Exception {
        Product existingProduct = getProductById(id);
        if(existingProduct != null) {
            Category existingCategory = categoryRepository.findById(body.getCategory_id()).orElseThrow(() ->
                    new DataNotFoundException("Category not found with id=" + body.getCategory_id()));
            existingProduct.setName(body.getName());
            existingProduct.setDescription(body.getDescription());
            existingProduct.setImageUrl(body.getImage_url());
            existingProduct.setType(body.getType().stream().collect(Collectors.joining(",")));
        return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) throws Exception {
        Optional<Product> productEntity = productRepository.findById(id);
        if(productEntity.isPresent()) {
            productRepository.delete(productEntity.get());
        }
    }

}
