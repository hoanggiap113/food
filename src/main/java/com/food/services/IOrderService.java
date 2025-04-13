package com.food.services;

import com.food.model.entities.OrderEntity;
import org.springframework.stereotype.Service;

@Service
public interface IOrderService {
    OrderEntity getOrderById(Long id);
}
