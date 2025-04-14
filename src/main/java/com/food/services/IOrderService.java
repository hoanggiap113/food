package com.food.services;

import com.food.model.entities.Order;
import org.springframework.stereotype.Service;

@Service
public interface IOrderService {
    Order getOrderById(Long id);
}
