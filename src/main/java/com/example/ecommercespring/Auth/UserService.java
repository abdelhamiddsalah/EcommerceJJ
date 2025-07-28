package com.example.ecommercespring.Auth;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class UserService {

    private  UserRepositry userRepo;
    private  PasswordEncoder passwordEncoder;
    private  JWTService jwtService;

    public UserResponse createUser(UserDto userDto){
       UserEntity userEntity = new UserEntity();
       userEntity.setEmail(userDto.getEmail());
       userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
       userEntity.setFirstName(userDto.getFirstName());
       userEntity.setLastName(userDto.getLastName());
       userEntity.setRoles(userDto.getRoles());

        // حفظ المستخدم
        UserEntity savedUser = userRepo.save(userEntity);
        // بناء UserDetails لتوليد التوكن
        CustomUserDetails userDetails = new CustomUserDetails(
                savedUser.getUsername(),
                savedUser.getPassword(),
                savedUser.getId(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + savedUser.getRoles().name()))
        );

       String token= jwtService.generateToken(userDetails);

       return new  UserResponse(token,savedUser.getRoles().name());
    }
}
