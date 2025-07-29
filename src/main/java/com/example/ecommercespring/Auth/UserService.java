package com.example.ecommercespring.Auth;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepositry userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Transactional
    public UserResponse createUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setRoles(userDto.getRoles());



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

        userEntity.setAddresses(addressEntities); // ✅

        UserEntity savedUser = userRepo.save(userEntity);
        CustomUserDetails userDetails = new CustomUserDetails(
                savedUser.getUsername(),
                savedUser.getPassword(),
                savedUser.getId(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + savedUser.getRoles().name()))
        );

        String token = jwtService.generateToken(userDetails);

        return new UserResponse(token, savedUser.getRoles().name());
    }
}
