package com.food.services;

import com.food.dto.request.ProductRequestDTO;
import com.food.dto.response.ProductDetailResponse;

import java.util.List;

public interface ProductService {
    List<ProductDetailResponse> getAllProduct();

    ProductDetailResponse getProductById(Long productId);

    void deleteProduct(Long productId);

    void deleteAllProduct();

    Long saveProduct(ProductRequestDTO request);

    ProductDetailResponse updateProduct(ProductRequestDTO request);
}
