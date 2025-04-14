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
public class Order extends AbstractEntity{

    @Column(name="status")
    private String status;

    @Column(name="total_price")
    private Double totalPrice;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.PERSIST})
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name="voucher_id")
    private Voucher voucherEntity;
}
