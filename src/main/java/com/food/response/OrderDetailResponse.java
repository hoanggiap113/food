package com.food.response;

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
    private int quantity;
    private Long productId;
    private String productName;
    private Double price;
    private String image_url;
    private Double totalPrice;
    public static OrderDetailResponse from(OrderItem orderItem) {
        OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .productName(orderItem.getProduct().getName())
                .image_url(orderItem.getProduct().getImageUrl())
                .productId(orderItem.getProduct().getId())
                .totalPrice(orderItem.getTotalPrice())
                .build();

        return orderDetailResponse;
    }
}
