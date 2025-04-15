package com.food.services;
import java.util.List;

import com.food.model.entities.Product;
import com.food.dto.ProductRequestDTO;
import com.food.response.ProductResponseDTO;

public interface IProductService {
    List<ProductResponseDTO> getAll();
    Product getProductById(Long id) throws Exception;
    Product saveProduct(ProductRequestDTO body) throws Exception;
    Product updateProduct(ProductRequestDTO body, Long id) throws Exception;
    void deleteProduct(Long id) throws Exception;
}
