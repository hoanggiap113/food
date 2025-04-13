package com.food.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="orders")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="status")
    private String status;

    @Column(name="total_price")
    private Double totalPrice;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.PERSIST})
    @JoinColumn(name="user_id")
    private UserEntity userEntity;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name="voucher_id")
    private VoucherEntity voucherEntity;
}
