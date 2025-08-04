package com.example.ecommercespring.Cart;

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

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepositry  userRepositry;

    @Autowired
    private ProductsRepo productsRepo;

   public CartEntity addToCarts(Long productId, Long userId) {

       UserEntity user = userRepositry.findById(userId)
               .orElseThrow(() -> new UserNotFoundException("User not found"));

       productEntity product = productsRepo.findById(productId)
               .orElseThrow(() -> new UserNotFoundException("Product not found"));

       CartEntity cart = cartRepo.findByUser(user)
               .orElseGet(() -> {
                   // لو معندوش كارت، انشئ كارت جديد
                   CartEntity newCart = new CartEntity();
                   newCart.setUser(user);
                   newCart.setProducts(new ArrayList<>());
                   newCart.setQuantity(0);
                   newCart.setTotalPrice(0.0);
                   return newCart;
               });

       cart.getProducts().add(product);
       cart.setQuantity(cart.getProducts().size()); // عدد المنتجات
       cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());

       return cartRepo.save(cart);
   }

   CartEntity getCart( Long userId) {
       List<CartEntity> cart = cartRepo.findAll();
       return cart.get(cart.size()-1);
   }
}
