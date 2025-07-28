package com.example.ecommercespring;

public class UserNotFoundException extends  Exception{
    public UserNotFoundException(String message){
        super(message);
    }
}

