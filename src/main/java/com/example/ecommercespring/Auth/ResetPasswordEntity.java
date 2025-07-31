package com.example.ecommercespring.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordEntity {
    private String email;
    private String password;
    private String ConfirmPassword;
}
