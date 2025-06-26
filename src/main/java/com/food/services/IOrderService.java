package com.food.services;

import java.util.List;

import com.food.dto.response.OrderResponseDTO;
import com.food.request.OrderDTO;
import com.food.model.entities.Order;
import org.springframework.stereotype.Service;

@Service
public interface IOrderService {
    OrderResponseDTO getOrderById(Long id);
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order updateOrder(Long id,OrderDTO orderDTO) throws Exception;
    void deleteOrder(Long id) throws Exception;
    void updateOrderStatus(Long orderId, String status);
    List<Order> findByUserId(Long userId);
    List<OrderResponseDTO> getAll();
    void updateOrderStatusAndPaymentStatus(Long orderId, String status, String paymentStatus);
}
