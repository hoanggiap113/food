package com.food.services.impl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.*;
import com.food.customexceptions.DataNotFoundException;
import com.food.dto.response.OrderResponseDTO;
import com.food.model.entities.*;
import com.food.repositories.*;
import com.food.request.OrderDTO;
import com.food.request.OrderItemDTO;
import com.food.response.OrderDetailResponse;
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
    private final PaymentRepository paymentRepository;
    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        Payment payment = paymentRepository.findByOrderId(id);
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO().builder()
                .id(order.getId().toString())
                .total(order.getTotalPrice().toString())
                .date(order.getCreatedAt().toString())
                .status(order.getStatus().toString())
                .full_name(order.getFullName())
                .phone(order.getPhoneNumber())
                .action(order.getActive() ? "Hoạt động" : "Không hoạt động")
                .payment_status(payment.getPaymentStatus().toString())
                .status(order.getStatus())
                .orderItems(orderItemRepository.findByOrderId(order.getId()).stream().map(OrderDetailResponse::from).collect(Collectors.toList()))
                .build();
        return orderResponseDTO;
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
        Payment payment = new Payment().builder()
                .order(savedOrder)
                .createdAt(LocalDateTime.now())
                .paymentMethod("cash")
                .paymentStatus("pending")
                .build();
        paymentRepository.save(payment);

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

    @Override
    public List<OrderResponseDTO> getAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> results = new ArrayList<>();

        for (Order order : orders) {
            Payment payment = paymentRepository.findByOrderId(order.getId());
            String paymentStatus = (payment != null && payment.getPaymentStatus() != null)
                    ? payment.getPaymentStatus().toString()
                    : "Chưa thanh toán";

            OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
                    .id(order.getId().toString())
                    .total(order.getTotalPrice().toString())
                    .date(order.getCreatedAt().toString())
                    .status(order.getStatus().toString())
                    .payment_status(paymentStatus)
                    .full_name(order.getFullName())
                    .build();

            results.add(orderResponseDTO);
        }

        return results;
    }

    @Override
    public void updateOrderStatusAndPaymentStatus(Long orderId, String status, String paymentStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find Order with id: " + orderId));
        order.setStatus(status);

        Payment payment = paymentRepository.findByOrderId(order.getId());
        if (payment != null) {
            payment.setPaymentStatus(mapPaymentStatus(paymentStatus));
            paymentRepository.save(payment);
        }

        orderRepository.save(order);
    }
    private String mapPaymentStatus(String inputStatus) {
        return switch (inputStatus.toLowerCase()) {
            case "completed" -> "COMPLETED";
            case "pending" -> "PENDING";
            case "failed" -> "FAILED";
            default -> "PENDING"; // fallback nếu sai chính tả
        };
    }



}
