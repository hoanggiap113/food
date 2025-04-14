package com.food.model.response;

import com.food.model.entities.OrderItem;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private Long id;
    private Long orderId;
    private Long userId;
    private Double price;
    private Long productId;
    private Double totalPrice;
    public static OrderDetailResponse from(OrderItem orderItem) {
        OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .userId(orderItem.getOrder().getUser().getId())
                .price(orderItem.getPrice())
                .productId(orderItem.getProduct().getId())
                .totalPrice(orderItem.getTotalPrice())
                .build();

        return orderDetailResponse;
    }
}
