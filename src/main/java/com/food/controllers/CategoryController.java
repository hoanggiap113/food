package com.food.controllers;
import java.util.List;
import com.food.model.entities.Category;
import com.food.response.CategoryResponse;
import com.food.services.impl.CategoriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private  CategoriesService categoriesService;

    @GetMapping("")
    public ResponseEntity<?> getProductsByCategory() {
        try{
            List<CategoryResponse> categoryList = categoriesService.getCategories();
            return ResponseEntity.ok().body(categoryList);
        }catch(Exception e){
            return ResponseEntity.status(500).body("Lỗi: " + e.getMessage());
        }
    }

}
