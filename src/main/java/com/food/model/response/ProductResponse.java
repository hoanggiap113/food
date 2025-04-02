package com.food.model.response;
import com.food.model.entities.BaseEntity;
import com.food.model.entities.ProductEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse extends BaseEntity {
    private String name;
    private String description;
    private Double price;
    private String image_url;
    private Long quantity;
    private String category_name;

    public static ProductResponse fromProduct(ProductEntity product){
        ProductResponse productResponse = ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image_url(product.getImageUrl())
                .category_name(product.getCategoryEntity().getName())
                .quantity(product.getQuantity())
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
