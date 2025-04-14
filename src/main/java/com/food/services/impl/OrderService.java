package com.food.services.impl;

import com.food.model.entities.Order;
import com.food.repositories.OrderRepository;
import com.food.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
