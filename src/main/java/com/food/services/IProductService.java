package com.food.services;
import java.util.List;
import com.food.model.response.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public interface IProductService {
    List<ProductResponse> getAll();
}
