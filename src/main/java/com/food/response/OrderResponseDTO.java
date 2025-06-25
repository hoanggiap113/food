package com.food.dto.response;

import com.food.response.OrderDetailResponse;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private String id;
    private String product;
    private String total;
    private String date;
    private String status;
    private String action;
    private List<OrderDetailResponse> orderItems;
}