package com.example.ecommercespring.Favourites;

import com.example.ecommercespring.Auth.UserEntity;
import com.example.ecommercespring.Auth.UserRepositry;
import com.example.ecommercespring.UserNotFoundException;
import com.example.ecommercespring.products.ProductsRepo;
import com.example.ecommercespring.products.productEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class FavouritesService {
    @Autowired
    private FavouritesRepo favouritesRepo;

    @Autowired
    private UserRepositry userRepositry;

    @Autowired
    private ProductsRepo products;

    FavouriteEntity AddToFavourites(Long userId, Long productId) {
        UserEntity user = userRepositry.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        productEntity product = products.findById(productId)
                .orElseThrow(() -> new UserNotFoundException("Product not found"));

        FavouriteEntity favouriteEntity = favouritesRepo.findByUser(userId).orElseGet(() -> {
            FavouriteEntity favouriteEntity1 = new FavouriteEntity();
            favouriteEntity1.setUser(user);
            favouriteEntity1.setNumberFavourites(0);
            favouriteEntity1.setProducts(new LinkedList<>());
            return favouriteEntity1;
        });

        favouriteEntity.getProducts().add(product);
        favouriteEntity.setNumberFavourites(favouriteEntity.getProducts().size());

        return favouritesRepo.save(favouriteEntity);
    }
}
