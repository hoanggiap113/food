package com.food.services.impl;
import java.util.*;

import com.food.customexceptions.DataNotFoundException;
import com.food.model.entities.Category;
import com.food.model.entities.Product;
import com.food.model.request.ProductRequestDTO;
import com.food.model.response.ProductResponse;
import com.food.repositories.CategoryRepository;
import com.food.repositories.ProductRepository;
import com.food.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.stream.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    @Override
    public List<ProductResponse> getAll() {
        List<ProductResponse> results = productRepository.findAll().stream().map((Product product) -> ProductResponse.fromProduct(product)).collect(Collectors.toList());
        return results;
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
                    .price(body.getPrice())
                    .imageUrl(body.getImage_url())
                    .quantity(body.getQuantity())
                    .status("available")
                    .type(body.getType().stream().collect(Collectors.joining(",")))
                    .categoryId(existingCategory)
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
            existingProduct.setPrice(body.getPrice());
            existingProduct.setImageUrl(body.getImage_url());
            existingProduct.setQuantity(body.getQuantity());
            existingProduct.setType(body.getType().stream().collect(Collectors.joining(",")));
        return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) throws Exception {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            productRepository.delete(product.get());
        }
    }

}
