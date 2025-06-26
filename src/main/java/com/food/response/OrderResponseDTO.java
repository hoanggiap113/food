package com.food.dto.response;

import com.food.model.entities.Order;
import com.food.response.OrderDetailResponse;
import lombok.*;
import java.util.stream.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class OrderResponseDTO {
    private String id;
    private String product;
    private String total;
    private String date;
    private String full_name;
    private String status;
    private String phone;
    private String payment_status;
    private String action;
    private List<OrderDetailResponse> orderItems;

}