package com.food.services;
import java.util.List;

import com.food.model.entities.ProductEntity;
import com.food.model.request.ProductRequestDTO;
import com.food.model.response.ProductResponse;

public interface IProductService {
    List<ProductResponse> getAll();
    ProductEntity getProductById(Long id) throws Exception;
    ProductEntity saveProduct(ProductRequestDTO body) throws Exception;
}
