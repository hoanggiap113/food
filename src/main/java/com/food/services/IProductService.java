package com.food.services;
import java.util.List;

import com.food.model.entities.Product;
import com.food.dto.ProductRequestDTO;
import com.food.response.ProductListResponse;
import com.food.response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {
    List<ProductResponseDTO> getAll();
    Page<ProductResponseDTO> getAllProduct(PageRequest pageRequest);
    Product getProductById(Long id) throws Exception;
    Product saveProduct(ProductRequestDTO body) throws Exception;
    Product updateProduct(ProductRequestDTO body, Long id) throws Exception;
    void deleteProduct(Long id) throws Exception;
}
