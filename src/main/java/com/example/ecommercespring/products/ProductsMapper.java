package com.example.ecommercespring.products;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductsMapper {
    productEntity toEntity(ProductDto productDto);
    ProductDto toDto(productEntity productEntity);

}
