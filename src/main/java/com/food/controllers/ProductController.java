package com.food.controllers;

import com.food.dto.request.ProductRequestDTO;
import com.food.dto.response.ProductDetailResponse;
import com.food.services.ProductService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/")
    public List<ProductDetailResponse> getAllProducts() {
        return productService.getAllProduct();
    }

    @GetMapping(value = "/{productId}")
    public ProductDetailResponse getProductById(@PathVariable @Min(value = 1, message = "productId must be greater than 0") Long productId) {
        return productService.getProductById(productId);
    }

    @DeleteMapping(value = "/{productId}")
    public void deleteProduct(@PathVariable @Min(value = 1, message = "productId must be greater than 0") Long productId) {
        log.info("Delete product with Id = {} success fully", productId);
        productService.deleteProduct(productId);
    }

    @DeleteMapping(value = "/")
    public void deleteAllProduct() {
        log.info("Delete all product successfully");
        productService.deleteAllProduct();
    }

    @PostMapping(value = "/")
    public Long addProduct(@RequestBody ProductRequestDTO request) {
        log.info("Add product successfully");

        return productService.saveProduct(request);
    }

    @PutMapping(value = "/")
    public ProductDetailResponse updateProduct(@RequestBody ProductRequestDTO request) {
        return productService.updateProduct(request);
    }

}
