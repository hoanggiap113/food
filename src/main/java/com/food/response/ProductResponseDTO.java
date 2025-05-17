package com.food.response;
import com.food.model.entities.BaseEntity;
import com.food.model.entities.Product;
import com.food.model.entities.ProductDetails;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO extends BaseEntity {
    private String name;
    private String description;
    private Double price;
    private String image_url;
    private Long quantity;
    private String category_name;
    private String type;
    public static ProductResponseDTO fromProduct(ProductDetails product){
        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder()
                .name(product.getProduct().getName())
                .description(product.getStatus())
                .price(product.getPrice())
                .image_url(product.getProduct().getImageUrl())
                .category_name(product.getProduct().getCategory().getName())
                .quantity(product.getQuantity())
                .build();
        return productResponseDTO;
    }
}
