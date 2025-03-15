package com.food.model.builders;

import java.time.LocalDateTime;
public class ProductBuilder {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long category_id;

    public ProductBuilder(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.imageUrl = builder.imageUrl;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.category_id = builder.category_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public static class Builder{
        private String name;
        private String description;
        private Double price;
        private String imageUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long category_id;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }
        public Builder setPrice(Double price) {
            this.price = price;
            return this;
        }
        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }
        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public Builder setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }
        public Builder setCategory_id(Long category_id) {
            this.category_id = category_id;
            return this;
        }
        public ProductBuilder build(){
            return new ProductBuilder(this);
        }
    }
}
