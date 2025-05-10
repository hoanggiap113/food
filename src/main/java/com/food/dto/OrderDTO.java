package com.food.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @JsonProperty("user_id")
    @Min(value = 1,message = "User id must be > 0")
    private Long userId;

    @JsonProperty("full_name")
    private String fullName;

    private String address;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    @Size(message = "Phone number must be at least 5 characters")
    private String phoneNumber;

    private String note;

    @JsonProperty("total_price")
    @Min(value = 0,message = "Total price must be > 0=")
    private Double totalPrice;

    @JsonProperty("voucher_id")
    private Long voucherId;

}
