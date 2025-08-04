package com.example.ecommercespring.Cart;

import com.example.ecommercespring.products.productEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private List<productEntity> products;

    private Double totalPrice;

    private int quantity;
}
