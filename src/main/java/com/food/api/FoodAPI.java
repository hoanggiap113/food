package com.food.api;
import java.util.*;

import com.food.DTO.ProductDTO;
import com.food.model.entities.ProductEntity;
import com.food.model.response.ProductResponse;
import com.food.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@RestController
public class FoodAPI {
    @Autowired
    private FoodService foodService;
    @GetMapping("/get")
    public List<ProductResponse> getAll(){
        List<ProductResponse> lists = foodService.getAll();
        return lists;
    }
}
