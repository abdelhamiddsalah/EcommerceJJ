package com.example.ecommercespring.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepo  productsRepo;

    productEntity AddProduct(ProductDto productDto){
        productEntity productEntity = new productEntity();
        productEntity.setName(productDto.getName());
        productEntity.setShortDescription(productDto.getShortDescription());
        productEntity.setLongDescription(productDto.getLongDescription());
        productEntity.setPrice(productDto.getPrice());

        return productsRepo.save(productEntity);
    }

    List<productEntity> GetAllProducts(){
        return productsRepo.findAll();
    }

    public List<productEntity> deleteProductById(Long id) {
        productsRepo.deleteById(id);
        return productsRepo.findAll();
    }

    public List<productEntity> bestSellingProducts() {
        List<productEntity> products = productsRepo.findAll();

        return products.stream()
                .sorted((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice())) // ترتيب تنازلي حسب السعر
                .limit(10) // نجيب أول 10 منتجات فقط
                .collect(Collectors.toList());
    }


}
