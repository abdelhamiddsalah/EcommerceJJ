package com.example.ecommercespring.Orders;

import com.example.ecommercespring.products.productEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ordersQuantities")
@Entity
public class OrdersQuantities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" ,nullable = false)
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "orderquantity")
    private productEntity product;

}
