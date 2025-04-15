package com.food.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
    @JsonProperty("cart_id")
    private Long cartId;
    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("price")
    private Double price;
}
