package com.food.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {
        @NotBlank(message = "Product name required")
        private String name;

        private String description;

        private Double price;

        private String image_url;

        @NotNull(message = "Category ID is required")
        private Long category_id;


        public String getName() {
                return name;
        }

        public String getDescription() {
                return description;
        }

        public Double getPrice() {
                return price;
        }


        public String getImage_url() {
                return image_url;
        }

        public Long getCategory_id() {
                return category_id;
        }

}
