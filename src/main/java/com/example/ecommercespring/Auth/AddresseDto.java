package com.example.ecommercespring.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddresseDto {


    private String Addressline1;
    private String Addressline2;

    private String city;
    private String country;
    private int postcode;
    private boolean active;
}
