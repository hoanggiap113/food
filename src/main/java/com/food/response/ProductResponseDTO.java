package com.food.response;
import com.food.model.entities.BaseEntity;
import com.food.model.entities.Product;
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

    public static ProductResponseDTO fromProduct(Product product){
        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image_url(product.getImageUrl())
                .category_name(product.getCategory().getName())
                .quantity(product.getQuantity())
                .build();
        return productResponseDTO;
    }
}
