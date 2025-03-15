package com.food.services.impl;

import com.food.DTO.ProductDTO;
import com.food.converter.ProductConverter;
import com.food.model.builders.ProductBuilder;
import com.food.model.entities.CategoryEntity;
import com.food.model.entities.ProductEntity;
import com.food.model.request.ProductRequest;
import com.food.model.response.ProductResponse;
import com.food.repositories.CategoryRepository;
import com.food.repositories.FoodRepository;
import com.food.repositories.custom.CustomFoodRepository;
import com.food.services.FoodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private ProductConverter productConverter;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CustomFoodRepository customFoodRepository;
    @Override

    public List<ProductResponse> getAll() {
        List<ProductEntity> lists = foodRepository.findAll();
//        List<ProductDTO> lists_dto = productConverter.EntityToDTO(lists);
//        List<ProductResponse> results = productConverter.DTOtoResponseList(lists_dto);
        List<ProductResponse> results = new ArrayList<>();
        for (ProductEntity productEntity : lists) {
            ProductResponse productResponse = productConverter.EntitytoResponse(productEntity);
            results.add(productResponse);
        }
        return results;
    }

    @Override
    public ProductResponse getById(Long id) {
        ProductEntity productEntity = foodRepository.findById(id).get();
        ProductDTO productDTO = modelMapper.map(productEntity, ProductDTO.class);
        ProductResponse productResponse = modelMapper.map(productDTO, ProductResponse.class);
        return productResponse;
    }
    @Override
    public ProductEntity save(ProductRequest productRequest) {
        String name = productRequest.getName();
        ProductEntity found = foodRepository.findByName(name);
        if (found != null) {
            return null;
        }
        Long category_id = productRequest.getCategory_id();
        CategoryEntity categoryEntity = null;
        if (category_id != null) {
            categoryEntity = categoryRepository.findById(category_id).orElse(null);
        }else{
            throw new RuntimeException("Category id not found");
        }

        ProductEntity productEntity = modelMapper.map(productRequest, ProductEntity.class);
        productEntity.setCategoryEntity(categoryEntity);
        productEntity.setCreatedAt(LocalDateTime.now());
        productEntity.setUpdatedAt(LocalDateTime.now());

        return foodRepository.save(productEntity);
    }

    @Override
    public List<ProductResponse> getProducts(Map<String, Object> params) {
        ProductBuilder productBuilder = productConverter.toProductBuilder(params);
        List<ProductEntity> productEntities = customFoodRepository.findProduct(productBuilder);
        List<ProductResponse> results = new ArrayList<>();
        for (ProductEntity productEntity : productEntities) {
            ProductResponse productResponse = productConverter.EntitytoResponse(productEntity);
            results.add(productResponse);
        }
        return results;
    }

}
