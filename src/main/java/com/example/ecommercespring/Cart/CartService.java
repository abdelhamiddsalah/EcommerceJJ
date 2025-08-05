package com.example.ecommercespring.Cart;

import com.example.ecommercespring.Auth.JWTService;
import com.example.ecommercespring.Auth.UserEntity;
import com.example.ecommercespring.Auth.UserRepositry;
import com.example.ecommercespring.UserNotFoundException;
import com.example.ecommercespring.products.ProductsRepo;
import com.example.ecommercespring.products.productEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepositry  userRepositry;

    @Autowired
    private ProductsRepo productsRepo;

    @Autowired
    private JWTService  jwtService;

    public CartEntity addToCarts(Long productId, String token) {
        Long userId = jwtService.extractId(token);
        List<String> roles = jwtService.extractRoles(token);

        if (roles.contains("ADMIN")) {
            throw new UserNotFoundException("Admins are not allowed to add to cart");
        }

        UserEntity user = userRepositry.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        productEntity product = productsRepo.findById(productId)
                .orElseThrow(() -> new UserNotFoundException("Product not found"));

        CartEntity cart = cartRepo.findByUser(user)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUser(user);
                    newCart.setProducts(new ArrayList<>());
                    newCart.setQuantity(0);
                    newCart.setTotalPrice(0.0);
                    return newCart;
                });

        if (cart.getProducts().contains(product)) {
            cart.setQuantity(cart.getQuantity() + 1);
            cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
        } else {
            cart.getProducts().add(product);
            cart.setQuantity(cart.getQuantity() + 1);
            cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
        }

        return cartRepo.save(cart);

}

   CartEntity getCart( Long userId) {
       List<CartEntity> cart = cartRepo.findAll();
       return cart.get(cart.size()-1);
   }

    public CartEntity deleteProductInCart(Long productId, String token) {
        Long userId = jwtService.extractId(token);
        List<String> roles = jwtService.extractRoles(token);

        UserEntity user = userRepositry.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        productEntity product = productsRepo.findById(productId)
                .orElseThrow(() -> new UserNotFoundException("Product not found"));

        CartEntity cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new UserNotFoundException("Cart not found"));

        // تحقق هل المنتج موجود في الكارت
        boolean removed = cart.getProducts().removeIf(p -> p.getId().equals(productId));

        if (removed) {
            // فقط لو تم الحذف بالفعل، عدّل الكمية والسعر
            cart.setQuantity(cart.getQuantity() - 1);
            cart.setTotalPrice(cart.getTotalPrice() - product.getPrice());
        } else {
            throw new UserNotFoundException("Product not found in cart");
        }

        return cartRepo.save(cart);
    }


}
