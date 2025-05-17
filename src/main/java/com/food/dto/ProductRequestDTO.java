package com.food.dto;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
        @Size(max = 500, message = "Image URL must be less than 500 characters")
        private String image_url;
        @NotNull(message = "Category ID is required")
        private Long category_id;
        private List<String> type;

}
