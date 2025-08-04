package com.example.ecommercespring.Cart;

import com.example.ecommercespring.Auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<CartEntity,Long> {
    Optional<CartEntity> findByUser(UserEntity user);
}
