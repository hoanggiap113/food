package com.food.model.response;

import lombok.Data;

import java.sql.Date;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
}
