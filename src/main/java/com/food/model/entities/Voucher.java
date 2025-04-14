package com.food.model.entities;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="code")
    private String code;

    @Column(name="discount_percent")
    private Float discountPercent;

    @Column(name="valid_from")
    private LocalDateTime validFrom;

    @Column(name="valid_to")
    private LocalDateTime validTo;

    @Column(name = "created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

}
