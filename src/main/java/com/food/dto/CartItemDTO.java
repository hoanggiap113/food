package com.food.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {

    private Long cartId;

    private Long productId;

    private Integer quantity;

    private Double price;
}
