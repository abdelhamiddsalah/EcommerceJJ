package com.example.ecommercespring.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
