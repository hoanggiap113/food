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


    @GetMapping("")
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
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try{
            ProductResponseDTO productResponseDTO = productService.findById(id);
            return ResponseEntity.ok(productResponseDTO);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping()
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {

        try{
            ProductRequestDTO product = productService.saveProduct(productRequestDTO);
            return ResponseEntity.ok(product);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deleteProducts(@RequestBody List<Long> ids) {
        try {
            productService.deleteProduct(ids);
            return ResponseEntity.ok("Đã xoá các sản phẩm thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO, @PathVariable Long id) {
        try{
            productService.updateProduct(productRequestDTO,id);
            return ResponseEntity.ok().body("Đã lưu thành công");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}