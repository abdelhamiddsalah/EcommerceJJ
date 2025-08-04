package com.example.ecommercespring.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/user/{UserId}/product/{ProductID}")
    @PreAuthorize("hasRole('USER')")
    CartEntity AddToCart(
            @PathVariable Long productId, @PathVariable Long USerID
    ){
        return cartService.addToCarts(productId, USerID);
    }

    @GetMapping("/user/{UserId}/carts")
    @PreAuthorize("hasRole('USER')")

    CartEntity GetCart( @PathVariable Long USerID){
        return cartService.getCart(USerID);
    }
}
