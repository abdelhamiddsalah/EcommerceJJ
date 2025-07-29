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
    @NotBlank(message = "Username is required")
    @Size(min = 4,max = 12)
    private String username;
    @NotBlank(message = "password is  required")
    @Size(min = 6)
    private String password;
    @Email
    @NotBlank(message = "Email is  requires")
    private String email;
    @NotBlank(message = "firstName is  required")
    private String firstName;
    @NotBlank(message = "LastName is  required")
    private String lastName;
    private RolesEnums roles;
    private List<AddresseDto> address;
}
