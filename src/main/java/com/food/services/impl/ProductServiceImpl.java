package com.food.services.impl;

import com.food.dto.request.ProductRequestDTO;
import com.food.dto.response.ProductDetailResponse;
import com.food.model.entities.Product;
import com.food.model.entities.Category;
import com.food.repositories.ProductRepository;
import com.food.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDetailResponse> getAllProduct() {
        log.info("Request get all list product");

        return productRepository.findAll().stream()
                .map(product -> ProductDetailResponse.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .description(product.getDescription())
                        .status(product.getStatus())
                        .imageUrl(product.getImageUrl())
                        .categoryId(product.getCategoryId().getId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetailResponse getProductById(Long productId) {
        log.debug("Check if product is valid with productId = {}", productId);
        return productRepository.findById(productId)
                .map(product -> {
                    log.info("Find product: {}", product.getName());
                    return ProductDetailResponse.builder()
                            .name(product.getName())
                            .price(product.getPrice())
                            .description(product.getDescription())
                            .imageUrl(product.getImageUrl())
                            .quantity(product.getQuantity())
                            .status(product.getStatus())
                            .categoryId(product.getCategoryId().getId())
                            .build();
                })
                .orElseGet(() -> {
                    log.warn("Can not find product with id: {}", productId);
                    return null;
                });
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public void deleteAllProduct() {
        productRepository.deleteAll();
    }

    @Override
    public Long saveProduct(ProductRequestDTO request) {

        Category category = new Category();
        category.setId(request.getCategoryId());

        Product product = Product.builder()
                .name(request.getName())
                .categoryId(category)
                .description(request.getDescription())
                .quantity(request.getQuantity())
                .imageUrl(request.getImageUrl())
                .price(request.getPrice())
                .status(request.getStatus())
                .build();

        productRepository.save(product);

        return product.getId();
    }


    @Override
    public ProductDetailResponse updateProduct(ProductRequestDTO request) {
        return null;
    }
}
