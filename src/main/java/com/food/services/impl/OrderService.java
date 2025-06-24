package com.food.services.impl;

import com.food.customexceptions.DataNotFoundException;
import com.food.model.entities.OrderItem;
import com.food.model.entities.Product;
import com.food.repositories.*;
import com.food.request.OrderDTO;
import com.food.model.entities.Order;
import com.food.model.entities.User;
import com.food.request.OrderItemDTO;
import com.food.services.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final VoucherRepository voucherRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find Order with id: " + id));
    }
    @Transactional
    public Order createOrder(OrderDTO orderDTO) {

        // Tìm user nếu userId không null
        User existUser = null;
        if (orderDTO.getUserId() != null) {
            existUser = userRepository.findById(orderDTO.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User with ID " + orderDTO.getUserId() + " not found"));
        }

        // Tạo Order
        Order order = Order.builder()
                .user(existUser)
                .address(orderDTO.getAddress())
                .note(orderDTO.getNote())
                .totalPrice(orderDTO.getTotalPrice())
                .fullName(orderDTO.getFullName())
                .phoneNumber(orderDTO.getPhoneNumber())
                .status("pending")
                .build();

        // Lưu Order
        Order savedOrder = orderRepository.save(order);

        // Tạo và lưu OrderItems
        List<OrderItem> orderItems = orderDTO.getOrderItems().stream().map(itemDTO -> {
            Product product = productRepository.findById(itemDTO.getProductId()).get();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(itemDTO.getPrice());
            orderItem.setTotalPrice(itemDTO.getTotalPrice());
            return orderItem;
        }).toList();

        orderItemRepository.saveAll(orderItems);

        return savedOrder;
    }

    @Override
    public Order updateOrder(Long id,OrderDTO orderDTO) throws Exception {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));
        User existingUser = userRepository.findById((orderDTO.getUserId())).orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + orderDTO.getUserId()));
        modelMapper.map(orderDTO, existingOrder);
        existingOrder.setUser(existingUser);
        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));
        if(existingOrder != null){
            existingOrder.setActive(false);
            orderRepository.save(existingOrder);
        }
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find Order with id: " + orderId));
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

}
