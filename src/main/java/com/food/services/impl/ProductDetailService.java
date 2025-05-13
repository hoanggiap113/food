package com.food.services.impl;

import com.food.customexceptions.DataNotFoundException;
import com.food.model.entities.ProductDetails;
import com.food.repositories.ProductDetailsRepository;
import com.food.services.IProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailService implements IProductDetailsService {
    private final ProductDetailsRepository productDetailRepository;
    @Override
    public ProductDetails getProductDetails(Long productDetailsId) {
        return productDetailRepository.findById(productDetailsId).orElseThrow(() -> new DataNotFoundException("Product not found with id=" + productDetailsId));
    }

    @Override
    public List<ProductDetails> getProductDetailsList(Long productId) {
        return List.of();
    }

    @Override
    public ProductDetails createProductDetails(ProductDetails productDetails, Long productId) {
        return null;
    }

}
