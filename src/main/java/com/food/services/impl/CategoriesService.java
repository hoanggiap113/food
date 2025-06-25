package com.food.services.impl;

import com.food.model.entities.Category;
import com.food.repositories.CategoriesRepository;
import com.food.response.CategoryResponse;
import com.food.services.ICategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriesService implements ICategoriesService {
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public List<CategoryResponse> getCategories() {
        return categoriesRepository.findAll().stream().map(CategoryResponse::from).collect(Collectors.toList());
    }
}
