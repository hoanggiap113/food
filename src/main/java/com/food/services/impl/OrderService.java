package com.food.services.impl;

import com.food.customexceptions.DataNotFoundException;
import com.food.dto.OrderDTO;
import com.food.model.entities.Order;
import com.food.model.entities.User;
import com.food.model.entities.Voucher;
import com.food.repositories.OrderRepository;
import com.food.repositories.UserRepository;
import com.food.repositories.VoucherRepository;
import com.food.services.IOrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final VoucherRepository voucherRepository;
    @Override
    public Order getOrderById(Long id) {

        return orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find Order with id: " + id));
    }
    @Override
    public Order createOrder(OrderDTO orderDTO) {
        User existUser = userRepository.findById(orderDTO.getUserId()).orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + orderDTO.getUserId()));
        Order order = new Order().builder()
                .user(existUser)
                .address(orderDTO.getAddress())
                .note(orderDTO.getNote())
                .totalPrice(orderDTO.getTotalPrice())
                .fullName(orderDTO.getFullName())
                .phoneNumber(orderDTO.getPhoneNumber())
                .status("pending")
                .build();
        return orderRepository.save(order);
    }
}
