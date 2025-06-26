    package com.food.response;
    import com.food.model.entities.BaseEntity;
    import com.food.model.entities.Product;
    import com.food.model.entities.ProductInventory;
    import lombok.*;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class ProductResponseDTO {
        private Long id;
        private String name;
        private String description;
        private Double price;
        private String image_url;
        private Long quantity;
        private Long category_id;
        private String category_name;
        private String type;
        private String status;

        public static ProductResponseDTO from(Product product, ProductInventory productInventory) {
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setId(product.getId());
            productResponseDTO.setName(product.getName());
            productResponseDTO.setDescription(product.getDescription());
            productResponseDTO.setPrice(productInventory.getPrice());
            productResponseDTO.setImage_url(product.getImageUrl());
            productResponseDTO.setCategory_id(product.getCategory().getId());
            productResponseDTO.setQuantity(productInventory.getQuantity());
            productResponseDTO.setCategory_name(product.getCategory().getName());
            productResponseDTO.setType(product.getType());
            productResponseDTO.setStatus(productInventory.getStatus());
            return productResponseDTO;
        }
    }
