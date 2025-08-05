package com.example.ecommercespring.Favourites;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FavouritesController {
    @Autowired
    private FavouritesService favouritesService;

    @PostMapping("/addToFavourites/user/{USerID}/product/{productID}")
    FavouriteEntity AddToFavourites(@PathVariable Long USerID,@PathVariable Long productID){
        return favouritesService.AddToFavourites(USerID,productID);
    }
}
