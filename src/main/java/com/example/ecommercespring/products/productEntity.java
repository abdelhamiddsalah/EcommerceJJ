package com.example.ecommercespring.products;

import com.example.ecommercespring.Cateories.CategoryEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class productEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
   private String name;
   private String shortDescription;
   private String LongDescription;
    @Column(name = "price")
   private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity  category;
}
