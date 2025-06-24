package com.food.controllers;

import java.util.List;

import com.food.response.ProductResponseDTO;
import com.food.services.impl.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;
    @GetMapping()
    public ResponseEntity<?> getProducts() {
        try{
            List<ProductResponseDTO> products = productService.getAll();
            return ResponseEntity.ok(products);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id) {
        try{
            ProductResponseDTO products = productService.findById(id);
            return ResponseEntity.ok(products);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
