package com.food.repositories;

import com.food.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long userId);
}
