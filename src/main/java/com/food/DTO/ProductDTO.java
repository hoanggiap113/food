package com.food.DTO;

import lombok.Data;

import java.sql.Date;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;
    private Date created_at;
    private Date updated_at;
}
