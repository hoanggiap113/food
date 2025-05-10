package com.food.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Entity
@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="cart")
public class Cart extends BaseEntity{
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

}
