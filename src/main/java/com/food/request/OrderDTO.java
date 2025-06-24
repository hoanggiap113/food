package com.food.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    // userId có thể là null để hỗ trợ đặt hàng không cần đăng nhập
    private Long userId;

    private String fullName;

    private String address;

    @NotBlank(message = "Phone number is required")
    @Size(min = 5, message = "Phone number must be at least 5 characters")
    private String phoneNumber;

    private String note;

    @Min(value = 0, message = "Total price must be >= 0")
    private Double totalPrice;

    private Long voucherId;

    // Thêm danh sách orderItems
    @JsonProperty("orderItems")
    private List<OrderItemDTO> orderItems;
}