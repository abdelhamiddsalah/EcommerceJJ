package com.example.ecommercespring.Cateories;

import com.example.ecommercespring.products.productEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<productEntity> product;
}
