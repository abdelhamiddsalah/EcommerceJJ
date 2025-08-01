package com.example.ecommercespring.Auth;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    UserResponse createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PostMapping("/login")
    UserResponse login(@Valid @RequestBody UserDto userDto) {
        return userService.Login(userDto);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        userService.processForgotPassword(email);
        return ResponseEntity.ok("Password reset link sent if email exists.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam("token") String token,
            @RequestBody ResetPasswordEntity resetPasswordEntity
    ) {
        return ResponseEntity.ok(userService.resetPassword(resetPasswordEntity, token));
    }

    @GetMapping("/AllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserEntity> getAllUsers() {
        return userService.getUsers();
    }


}
