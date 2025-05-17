package com.food.controllers;

import com.food.model.entities.ProductDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product_details")
public class ProductDetailsController {
    @PostMapping("")//Tạo bảng trạng thái cho sản phẩm
    public ResponseEntity<?> createProductDetails(@RequestBody ProductDetails productDetails) {
        return null;
    }
}
