package com.food.controllers;

import com.food.dto.response.OrderResponseDTO;
import com.food.request.OrderDTO;
import com.food.model.entities.Order;
import com.food.request.OrderStatusDTO;
import com.food.response.OrderDetailResponse;
import com.food.services.impl.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @GetMapping("")
    public ResponseEntity<?> getOrder() {
        try {
            List<OrderResponseDTO> orderResponseDTOS = orderService.getAll();
            return ResponseEntity.ok(orderResponseDTOS);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long orderId) {
        try {
            OrderResponseDTO order = orderService.getOrderById(orderId);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessage = bindingResult.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Order orderResponse = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @RequestBody OrderDTO orderDTO, @Valid @PathVariable("id") Long orderId) {
        try {
            Order order = orderService.updateOrder(orderId, orderDTO);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@Valid @PathVariable("id") Long orderId) throws Exception {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order deleted");
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateOrderStatus(@Valid @PathVariable("id") Long order,@RequestParam("status") String status,@RequestParam("payment_status") String paymentStatus) {
        try{
            orderService.updateOrderStatusAndPaymentStatus(order, status, paymentStatus);
            return ResponseEntity.ok("Order updated");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable Long user_id) {
        try {
            List<Order> orders = orderService.findByUserId(user_id);
            List<OrderResponseDTO> orderDTOs = orders.stream()
                    .map(order -> {
                        String productNames = order.getOrderItems() != null
                                ? order.getOrderItems().stream()
                                .map(item -> item.getProduct() != null && item.getProduct().getName() != null
                                        ? item.getProduct().getName()
                                        : "Sản phẩm " + (item.getProduct() != null ? item.getProduct().getId() : "Không xác định"))
                                .collect(Collectors.joining(", "))
                                : "";
                        String status = order.getStatus() != null ? order.getStatus().toLowerCase() : "processing";
                        String action = status.equals("confirmed") ? "Đánh giá" :
                                status.equals("Pending") ? "Thanh toán" : "Chi tiết";
                        return OrderResponseDTO.builder()
                                .id(order.getId().toString())
                                .product(productNames)
                                .total(formatCurrency(order.getTotalPrice()))
                                .date(formatDate(order.getCreatedAt()))
                                .status(status)
                                .action(action)
                                .orderItems(order.getOrderItems() != null
                                        ? order.getOrderItems().stream()
                                        .map(OrderDetailResponse::from)
                                        .collect(Collectors.toList())
                                        : List.of())
                                .build();
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(orderDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String formatCurrency(Double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(amount != null ? amount : 0);
    }

    private String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return new java.util.Date().toInstant().atZone(java.time.ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}