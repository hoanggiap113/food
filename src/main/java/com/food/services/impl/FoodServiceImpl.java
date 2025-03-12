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
import com.food.services.FoodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private ProductBuilder productBuilder;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override

    public List<ProductResponse> getAll() {
        List<ProductEntity> lists = foodRepository.findAll();
        List<ProductDTO> lists_dto = productConverter.EntityToDTO(lists);
        List<ProductResponse> results = productConverter.DTOtoResponseList(lists_dto);
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
            return null; // Sản phẩm đã tồn tại
        }
        Long category_id = productRequest.getCategory_id();
        CategoryEntity categoryEntity = null;
        // Kiểm tra xem categoryId đã tồn tại chưa
        if (category_id != null) {
            categoryEntity = categoryRepository.findById(category_id).orElse(null);
        }

        // Tạo ProductEntity và gán CategoryEntity
        ProductEntity productEntity = modelMapper.map(productRequest, ProductEntity.class);
        productEntity.setCategoryEntity(categoryEntity);
        productEntity.setCreatedAt(LocalDateTime.now());
        productEntity.setUpdatedAt(LocalDateTime.now());

        return foodRepository.save(productEntity);
    }

}
