package com.example.ecommercespring.Cateories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.jackson2.CoreJackson2Module;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findById(Long id);
}
