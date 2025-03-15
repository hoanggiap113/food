package com.food.services;
import java.util.List;
import java.util.Map;

import com.food.DTO.ProductDTO;
import com.food.model.entities.ProductEntity;
import com.food.model.request.ProductRequest;
import com.food.model.response.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public interface FoodService {
    List<ProductResponse> getAll();
    ProductResponse getById(Long id);
    ProductEntity save(ProductRequest productRequest);
    List<ProductResponse> getProducts(Map<String,Object> params);
}
