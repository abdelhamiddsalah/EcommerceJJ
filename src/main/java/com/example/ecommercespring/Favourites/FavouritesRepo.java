package com.example.ecommercespring.Favourites;

import com.example.ecommercespring.Auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouritesRepo extends JpaRepository<FavouriteEntity,Long> {
    Optional<FavouriteEntity> findByUser(UserEntity user);
}
