package com.food.services.impl;

import com.food.DTO.ProductDTO;
import com.food.converter.ProductConverter;
import com.food.model.entities.ProductEntity;
import com.food.model.response.ProductResponse;
import com.food.repositories.FoodRepository;
import com.food.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private ProductConverter productConverter;
    @Override

    public List<ProductResponse> getAll() {
        List<ProductEntity> lists = foodRepository.findAll();
        List<ProductDTO> lists_dto = productConverter.EntityToDTO(lists);
        List<ProductResponse> results = productConverter.DTOtoResponseList(lists_dto);
        return results;
    }
}
