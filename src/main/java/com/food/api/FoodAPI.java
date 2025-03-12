package com.food.api;
import java.util.*;

import com.food.DTO.ProductDTO;
import com.food.model.entities.ProductEntity;
import com.food.model.request.ProductRequest;
import com.food.model.response.ProductResponse;
import com.food.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/get/{id}")
    public ProductResponse getProductById(@PathVariable Long id){
        ProductResponse result = foodService.getById(id);
        return result;
    }

//    @PostMapping("/save")
//    public ProductResponse saveProduct(@Path){
//
//    }
    @PostMapping("/add")
    public ProductEntity addProduct(@RequestBody ProductRequest productRequest){
        return foodService.save(productRequest);
    }
}
