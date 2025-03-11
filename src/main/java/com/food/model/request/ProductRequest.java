package com.food.model.request;

import lombok.Getter;

import java.sql.Date;

@Getter
public class ProductRequest {
        private Long id;
        private String name;
        private String description;
        private Double price;
        private String image;
        private Date created_at;
        private Date updated_at;
}
