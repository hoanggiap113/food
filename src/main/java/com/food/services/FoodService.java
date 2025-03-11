package com.food.services;
import java.util.List;

import com.food.DTO.ProductDTO;
import com.food.model.response.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public interface FoodService {
    List<ProductResponse> getAll();
}
