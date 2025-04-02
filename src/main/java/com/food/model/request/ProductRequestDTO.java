package com.food.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRequestDTO {
        @NotBlank(message = "Product name required")
        private String name;

        private String description;

        private Double price;

        private String image_url;

        @NotNull(message = "Category ID is required")
        private Long category_id;

        private Long quantity;

        private String status;

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

        public Long getQuantity() {
                return quantity;
        }

        public String getStatus() {
                return status;
        }
}
