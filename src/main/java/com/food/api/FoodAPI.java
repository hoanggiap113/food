package com.food.api;
import java.util.*;

import com.food.model.request.ProductRequestDTO;
import com.food.model.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://127.0.0.1:5500")

@RequestMapping("")
@RestController
@Validated
public class FoodAPI {
    @Autowired
    private com.food.services.IProductService IProductService;
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return new ResponseEntity<>(IProductService.getAll(), HttpStatus.OK);
    }

    @PutMapping("products/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO){
        return null;
    }

}
