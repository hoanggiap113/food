package com.food.services;
import java.util.List;

import com.food.model.entities.Product;
import com.food.model.request.ProductRequestDTO;
import com.food.model.response.ProductResponse;

public interface IProductService {
    List<ProductResponse> getAll();
    Product getProductById(Long id) throws Exception;
    Product saveProduct(ProductRequestDTO body) throws Exception;
    Product updateProduct(ProductRequestDTO body,Long id) throws Exception;
    void deleteProduct(Long id) throws Exception;
}
