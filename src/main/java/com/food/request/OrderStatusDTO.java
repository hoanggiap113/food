package com.food.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDTO {
    private Long orderId;
    @JsonProperty("status")
    private String orderStatus;

    @JsonProperty("payment_status")
    private String paymentStatus;
}
