package com.food.model.request;

import lombok.Getter;

import java.sql.Date;

public class ProductRequest {
        private Long id;
        private String name;
        private String description;
        private Double price;
        private String image;
        private Long category_id;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public Double getPrice() {
                return price;
        }

        public void setPrice(Double price) {
                this.price = price;
        }

        public String getImage() {
                return image;
        }

        public void setImage(String image) {
                this.image = image;
        }

        public Long getCategory_id() {
                return category_id;
        }

        public void setCategory_id(Long category_id) {
                this.category_id = category_id;
        }
}
