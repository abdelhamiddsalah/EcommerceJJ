package com.example.ecommercespring.Auth;
import com.example.ecommercespring.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepositry userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;


    @Transactional
    public UserResponse createUser(UserDto userDto) {

        Optional<UserEntity> existingUser = userRepo.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            throw new UserNotFoundException("User with email " + userDto.getEmail() + " already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        RolesEnums roleEnum;
        try {
            roleEnum = RolesEnums.valueOf(userDto.getRoles().toUpperCase()); // يحول النص إلى enum
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new UserNotFoundException("Invalid role: must be either USER or ADMIN");
        }
        userEntity.setRoles(roleEnum);

        List<AddressEntity> addressEntities = new ArrayList<>();

        for (AddresseDto addressDTO : userDto.getAddress()) {
            AddressEntity address = new AddressEntity();
            address.setAddressline1(addressDTO.getAddressline1());
            address.setAddressline2(addressDTO.getAddressline2());
            address.setCity(addressDTO.getCity());
            address.setCountry(addressDTO.getCountry());
            address.setPostcode(addressDTO.getPostcode());
            address.setActive(addressDTO.isActive());
            address.setUser(userEntity); // مهم
            addressEntities.add(address);
        }

        userEntity.setAddress(addressEntities); // ✅

        UserEntity savedUser = userRepo.save(userEntity);


        CustomUserDetails userDetails = new CustomUserDetails(
                savedUser.getEmail(),
                savedUser.getPassword(),
                savedUser.getId(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + savedUser.getRoles().name()))
        );

        String token = jwtService.generateToken(userDetails);

        return new UserResponse(token, savedUser.getRoles().name());
    }

    UserResponse Login(UserDto userDto) {
        // 1. ابحث عن المستخدم في قاعدة البيانات
        UserEntity user = userRepo.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userDto.getEmail()));
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

// ✅ تحقق من الباسورد
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid password");
        }

// ✅ استخدم بيانات المستخدم الحقيقي
        CustomUserDetails userDetails = new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getId(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRoles().name()))
        );

        String token = jwtService.generateToken(userDetails);

        return new UserResponse(token, user.getRoles().name());

    }

    public void processForgotPassword(String email) {
        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        if (user == null) return;

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30)); // صلاحية 30 دقيقة

        tokenRepository.save(resetToken);

        String resetLink = "http://localhost:8080/reset-password?token=" + token;
        emailService.sendEmail(user.getEmail(), "Reset your password", resetLink);
    }

    public String resetPassword(ResetPasswordEntity resetPasswordEntity, String token) {
        PasswordResetToken tokenEntity = tokenRepository.findByToken(token);

        if (tokenEntity == null || tokenEntity.isExpired()) {
            throw new RuntimeException("Token invalid or expired");
        }

        UserEntity user = tokenEntity.getUser();

        if (!resetPasswordEntity.getPassword().equals(resetPasswordEntity.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(resetPasswordEntity.getPassword()));
        userRepo.save(user);
        tokenRepository.delete(tokenEntity);

        return "Password reset successfully";
    }

    List<UserEntity> getUsers() {
        return userRepo.findAll();
    }
}
