package com.example.ecommercespring.Auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositry extends JpaRepository<UserEntity, Long> {
    // الخيار الأفضل
    Optional<UserEntity> getById(long id);
    Optional<UserEntity>  getByUsername(String username);
    Optional<UserEntity> findByEmail(String email);

    // أو لو عايز بشكل أوضح
    // UserEntity findUserEntityById(long id);
}
