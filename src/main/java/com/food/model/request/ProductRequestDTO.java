package com.food.model.request;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
        @NotBlank(message = "Product name required")
        private String name;
        private String description;
        @Min(value=0,message = "price must be greater than 0")
        private Double price;
        private String image_url;
        @NotNull(message = "Category ID is required")
        private Long category_id;
        private Long quantity;
        private List<String> type;


}
