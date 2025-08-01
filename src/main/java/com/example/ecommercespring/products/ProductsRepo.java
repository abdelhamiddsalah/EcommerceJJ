package com.example.ecommercespring.products;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepo extends JpaRepository<productEntity,Long> {

}
