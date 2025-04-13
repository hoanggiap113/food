package com.food.controllers;

import com.food.model.entities.ProductEntity;
import com.food.model.request.ProductRequestDTO;
import com.food.model.response.ProductResponse;
import com.food.model.response.ResponseData;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<ResponseData<List<ProductResponse>>> getProducts() {
        List<ProductResponse> products = iProductService.getAll();
        ResponseData<List<ProductResponse>> response = new ResponseData<>(
                HttpStatus.OK.value(),
                "Lấy danh sách sản phẩm thành công",
                products
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<ProductResponse>> getProductById(@PathVariable Long id) {
        try {
            ProductEntity product = iProductService.getProductById(id);
            if (product == null) {
                ResponseData<ProductResponse> response = new ResponseData<>(
                        HttpStatus.NOT_FOUND.value(),
                        "Không tìm thấy sản phẩm với ID: " + id,
                        null
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ResponseData<ProductResponse> response = new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Lấy thông tin sản phẩm thành công",
                    ProductResponse.fromProduct(product)
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseData<ProductResponse> response = new ResponseData<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null
            );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("")
    public ResponseEntity<ResponseData<ProductResponse>> saveProduct(
            @Valid @RequestBody ProductRequestDTO product,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(", "));

                ResponseData<ProductResponse> response = new ResponseData<>(
                        HttpStatus.BAD_REQUEST.value(),
                        "Dữ liệu không hợp lệ: " + errorMessage,
                        null
                );
                return ResponseEntity.badRequest().body(response);
            }

            ProductEntity savedProduct = iProductService.saveProduct(product);

            if (savedProduct != null) {
                ResponseData<ProductResponse> response = new ResponseData<>(
                        HttpStatus.CREATED.value(),
                        "Tạo sản phẩm thành công",
                        ProductResponse.fromProduct(savedProduct)
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                ResponseData<ProductResponse> response = new ResponseData<>(
                        HttpStatus.CONFLICT.value(),
                        "Tên sản phẩm '" + product.getName() + "' đã tồn tại",
                        null
                );
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
        } catch (Exception e) {
            ResponseData<ProductResponse> response = new ResponseData<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null
            );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<ProductResponse>> updateProduct(
            @Valid @RequestBody ProductRequestDTO product,
            BindingResult bindingResult,
            @PathVariable Long id) {
        try {
            if (bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(", "));

                ResponseData<ProductResponse> response = new ResponseData<>(
                        HttpStatus.BAD_REQUEST.value(),
                        "Dữ liệu không hợp lệ: " + errorMessage,
                        null
                );
                return ResponseEntity.badRequest().body(response);
            }

            ProductEntity updatedProduct = iProductService.updateProduct(product, id);

            if (updatedProduct == null) {
                ResponseData<ProductResponse> response = new ResponseData<>(
                        HttpStatus.NOT_FOUND.value(),
                        "Không tìm thấy sản phẩm với ID: " + id,
                        null
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            ResponseData<ProductResponse> response = new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Cập nhật sản phẩm thành công",
                    ProductResponse.fromProduct(updatedProduct)
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseData<ProductResponse> response = new ResponseData<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null
            );
            return ResponseEntity.badRequest().body(response);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<ProductResponse>> deleteProduct(
            @PathVariable Long id){
        try{
            productService.deleteProduct(id);
            ResponseData<ProductResponse> response = new ResponseData<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Xoa san pham thanh cong",
                    null
            );
            return ResponseEntity.ok(response);
        }catch(Exception e){
            ResponseData<ProductResponse> response = new ResponseData<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null
            );
            return ResponseEntity.badRequest().body(response);
        }
    }
}