package com.example.ecommercespring.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @Size(min = 3, max = 50)
    private String username;

    private String password;
    @Email(message = "Email is Error")
    private String email;

    private String firstName;

    private String lastName;

    private String roles;

    private List<AddresseDto> address;
}

