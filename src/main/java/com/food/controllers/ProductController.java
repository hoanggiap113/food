package com.food.controllers;

import java.util.List;

import com.food.request.ProductPresentRequestDTO;
import com.food.request.ProductRequestDTO;
import com.food.response.ProductPresentResponse;
import com.food.response.ProductResponseDTO;
import com.food.services.impl.FileStorageService;
import com.food.services.impl.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final FileStorageService fileStorageService;


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
    @PostMapping()
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO,
                                        @RequestParam("imageFile") MultipartFile imageFile) {

        try{
            ProductRequestDTO product = productService.saveProduct(productRequestDTO);
            return ResponseEntity.ok(product);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping() //Xóa mềm
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try{
            productService.deleteProduct(id);
            return ResponseEntity.ok("Xóa sản phẩm thành công");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductPresentRequestDTO productRequestDTO, @PathVariable Long id) {
        try{
            ProductPresentResponse product = productService.updateProduct(productRequestDTO,id);
            return ResponseEntity.ok(product);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}