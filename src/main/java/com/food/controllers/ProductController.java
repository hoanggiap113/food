package com.food.controllers;

import com.food.model.entities.Product;
import com.food.dto.ProductRequestDTO;
import com.food.response.ProductListResponse;
import com.food.response.ProductResponseDTO;
import com.food.response.ResponseData;
import com.food.services.FileStorageService;
import com.food.services.IProductService;
import com.food.services.impl.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService iProductService;
    private final ProductService productService;
    private final FileStorageService fileStorageService;

//    @GetMapping("")
//    public ResponseEntity<ResponseData<List<ProductResponseDTO>>> getProducts() {
//        List<ProductResponseDTO> products = iProductService.getAll();
//        ResponseData<List<ProductResponseDTO>> response = new ResponseData<>(
//                HttpStatus.OK.value(),
//                "Lấy danh sách sản phẩm thành công",
//                products
//        );
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("")
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        PageRequest pageRequest = PageRequest.of(
                page,limit,
                Sort.by("createdAt").descending());
        Page<ProductResponseDTO> productPage = productService.getAllProduct(pageRequest);
        int totalPages = productPage.getTotalPages();
        List<ProductResponseDTO> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse
                .builder()
                .products(products)
                .totalPages(totalPages)
                .build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<ProductResponseDTO>> getProductById(@PathVariable Long id) {
        try {
            Product product = iProductService.getProductById(id);
            if (product == null) {
                ResponseData<ProductResponseDTO> response = new ResponseData<>(
                        HttpStatus.NOT_FOUND.value(),
                        "Không tìm thấy sản phẩm với ID: " + id,
                        null
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ResponseData<ProductResponseDTO> response = new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Lấy thông tin sản phẩm thành công",
                    ProductResponseDTO.fromProduct(product)
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseData<ProductResponseDTO> response = new ResponseData<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null
            );
            return ResponseEntity.badRequest().body(response);
        }
    }

//    @PostMapping("")
//    public ResponseEntity<ResponseData<ProductResponseDTO>> saveProduct(
//            @Valid @RequestBody ProductRequestDTO product,
//            BindingResult bindingResult) {
//        try {
//            if (bindingResult.hasErrors()) {
//                String errorMessage = bindingResult.getFieldErrors()
//                        .stream()
//                        .map(FieldError::getDefaultMessage)
//                        .collect(Collectors.joining(", "));
//
//                ResponseData<ProductResponseDTO> response = new ResponseData<>(
//                        HttpStatus.BAD_REQUEST.value(),
//                        "Dữ liệu không hợp lệ: " + errorMessage,
//                        null
//                );
//                return ResponseEntity.badRequest().body(response);
//            }
//
//            Product savedProduct = iProductService.saveProduct(product);
//
//            if (savedProduct != null) {
//                ResponseData<ProductResponseDTO> response = new ResponseData<>(
//                        HttpStatus.CREATED.value(),
//                        "Tạo sản phẩm thành công",
//                        ProductResponseDTO.fromProduct(savedProduct)
//                );
//                return ResponseEntity.status(HttpStatus.CREATED).body(response);
//            } else {
//                ResponseData<ProductResponseDTO> response = new ResponseData<>(
//                        HttpStatus.CONFLICT.value(),
//                        "Tên sản phẩm '" + product.getName() + "' đã tồn tại",
//                        null
//                );
//                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
//            }
//        } catch (Exception e) {
//            ResponseData<ProductResponseDTO> response = new ResponseData<>(
//                    HttpStatus.BAD_REQUEST.value(),
//                    e.getMessage(),
//                    null
//            );
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<ProductResponseDTO>> saveProduct(
            @RequestPart("product") @Valid ProductRequestDTO productRequest,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            BindingResult bindingResult
    ) {
        try {
            // Validate data
            if (bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(", "));

                ResponseData<ProductResponseDTO> response = new ResponseData<>(
                        HttpStatus.BAD_REQUEST.value(),
                        "Dữ liệu không hợp lệ: " + errorMessage,
                        null);

                return ResponseEntity.badRequest().body(response);

            }
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = fileStorageService.store(imageFile);
                productRequest.setImage_url(imageUrl);
            }
            Product savedProduct = productService.saveProduct(productRequest);
            return ResponseEntity.ok(new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Tạo sản phẩm thành công",
                    ProductResponseDTO.fromProduct(savedProduct)
            ));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null)
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<ProductResponseDTO>> updateProduct(
            @Valid @RequestBody ProductRequestDTO product,
            BindingResult bindingResult,
            @PathVariable Long id) {
        try {
            if (bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(", "));

                ResponseData<ProductResponseDTO> response = new ResponseData<>(
                        HttpStatus.BAD_REQUEST.value(),
                        "Dữ liệu không hợp lệ: " + errorMessage,
                        null
                );
                return ResponseEntity.badRequest().body(response);
            }

            Product updatedProduct = iProductService.updateProduct(product, id);

            if (updatedProduct == null) {
                ResponseData<ProductResponseDTO> response = new ResponseData<>(
                        HttpStatus.NOT_FOUND.value(),
                        "Không tìm thấy sản phẩm với ID: " + id,
                        null
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            ResponseData<ProductResponseDTO> response = new ResponseData<>(
                    HttpStatus.OK.value(),
                    "Cập nhật sản phẩm thành công",
                    ProductResponseDTO.fromProduct(updatedProduct)
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseData<ProductResponseDTO> response = new ResponseData<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null
            );
            return ResponseEntity.badRequest().body(response);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<ProductResponseDTO>> deleteProduct(
            @PathVariable Long id){
        try{
            productService.deleteProduct(id);
            ResponseData<ProductResponseDTO> response = new ResponseData<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Xoa san pham thanh cong",
                    null
            );
            return ResponseEntity.ok(response);
        }catch(Exception e){
            ResponseData<ProductResponseDTO> response = new ResponseData<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null
            );
            return ResponseEntity.badRequest().body(response);
        }
    }
}