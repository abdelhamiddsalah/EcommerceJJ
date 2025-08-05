package com.example.ecommercespring.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/user/product/{ProductID}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public CartEntity AddToCart(
            @PathVariable("ProductID") Long productId,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        return cartService.addToCarts(productId, token);
    }



    @GetMapping("/user/{UserId}/carts")
    @PreAuthorize("hasRole('USER')")

    CartEntity GetCart( @PathVariable Long USerID){
        return cartService.getCart(USerID);
    }

    @DeleteMapping("/user/product/{ProductID}")
    @PreAuthorize("hasRole('USER')")
    public CartEntity DeleteProductInCart(@PathVariable Long ProductID,
                                          @RequestHeader("Authorization") String authHeader
                                          ){
        String token = authHeader.replace("Bearer ", "");
        return cartService.deleteProductInCart(ProductID, token);

    }

}
