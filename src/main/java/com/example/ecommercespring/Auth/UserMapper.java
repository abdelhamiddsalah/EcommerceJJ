package com.example.ecommercespring.Auth;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // مهم جداً لو عايز تستخدمه داخل @Service أو @RestController
public interface UserMapper {

    // تحويل من Entity إلى DTO
    UserDto toDto(UserEntity userEntity);

    // تحويل من DTO إلى Entity
    UserEntity toEntity(UserDto userDto);
}
