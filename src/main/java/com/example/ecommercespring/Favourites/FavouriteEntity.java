package com.example.ecommercespring.Favourites;

import com.example.ecommercespring.Auth.UserEntity;
import com.example.ecommercespring.products.productEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favourites")
public class FavouriteEntity {
    @Id
    private Long id;

    @OneToMany
    private List<productEntity> products;

    private int numberFavourites;

    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity  user;
}
