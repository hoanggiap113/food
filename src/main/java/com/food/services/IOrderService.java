package com.food.services;

import com.food.dto.OrderDTO;
import com.food.model.entities.Order;
import org.springframework.stereotype.Service;

@Service
public interface IOrderService {
    Order getOrderById(Long id);
    Order createOrder(OrderDTO orderDTO) throws Exception;
}
