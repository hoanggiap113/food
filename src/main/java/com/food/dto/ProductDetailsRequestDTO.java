package com.food.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsRequestDTO {

    @JsonProperty("product_id")
    @Min(value=1,message = "product id must be greater than 0")
    private Long productId;

    @JsonProperty("price")
    @Min(value=0,message = "price must be greater than 0")
    private Double price;

    @JsonProperty("is_apply")
    private Boolean isApply;

    @JsonProperty("status")
    private String status;


}
