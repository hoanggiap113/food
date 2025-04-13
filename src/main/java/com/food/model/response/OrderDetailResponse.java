package com.food.model.response;

import com.food.model.entities.OrderItemEntity;
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
    public static OrderDetailResponse from(OrderItemEntity orderItem) {
        OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .userId(orderItem.getOrder().getUserEntity().getId())
                .price(orderItem.getPrice())
                .productId(orderItem.getProduct().getId())
                .totalPrice(orderItem.getTotalPrice())
                .build();

        return orderDetailResponse;
    }
}
