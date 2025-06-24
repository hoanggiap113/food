package com.food.services;
import java.util.List;

import com.food.model.entities.Product;
import com.food.request.ProductPresentRequestDTO;
import com.food.request.ProductRequestDTO;
import com.food.response.ProductPresentResponse;
import com.food.response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {
    List<ProductResponseDTO> getAll();
    ProductRequestDTO saveProduct(ProductRequestDTO body) throws Exception;
    ProductPresentResponse updateProduct(ProductPresentRequestDTO body, Long id) throws Exception;
    void deleteProduct(Long id) throws Exception;
    ProductResponseDTO findById(Long id);
}
