package com.food.services;
import java.util.List;
import com.food.model.entities.ProductDetails;
import org.springframework.stereotype.Service;

@Service
public interface IProductDetailsService {
    ProductDetails getProductDetails(Long productId);
    List<ProductDetails> getProductDetailsList(Long productId);
    ProductDetails createProductDetails(ProductDetails productDetails, Long productId);
}
