package com.food.controllers;

import com.food.model.entities.ProductEntity;
import com.food.model.request.ProductRequestDTO;
import com.food.model.response.ProductResponse;
import com.food.services.IProductService;
import com.food.services.impl.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService iProductService;
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return new ResponseEntity<>(iProductService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try{
            ProductEntity product = iProductService.getProductById(id);
            return new ResponseEntity<>(ProductResponse.fromProduct(product), HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PostMapping("")
    public ResponseEntity<?> addProduct(
            @Valid @RequestBody ProductRequestDTO product,
            BindingResult bindingResult, @RequestParam(name = "type") List<String> typeList) {
        product.setType(typeList);
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            ProductEntity newProduct = productService.saveProduct(product);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
