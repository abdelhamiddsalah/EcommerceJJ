package com.example.ecommercespring.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class productsController {
    @Autowired
    private ProductsService productsService;

    @PostMapping("/addProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public productEntity addProduct(@RequestBody ProductDto productDto) {
        return  productsService.AddProduct(productDto);
    }

    @GetMapping("/products")
    @PreAuthorize("hasRole('USER')")
    public List<productEntity> GetAllProducts(){
        return productsService.GetAllProducts();
    }

    @DeleteMapping("/deleteProduct/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<productEntity> deleteProduct(@PathVariable Long id){
        return productsService.deleteProductById(id);
    }
}
