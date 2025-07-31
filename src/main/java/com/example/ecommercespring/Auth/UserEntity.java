package com.example.ecommercespring.Auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "username")
    private String username;
   @Column(unique = true,nullable = false,name = "password")
    private String password;
   @Column(nullable = false,unique = true,name = "email")
    private String email;
   @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private RolesEnums roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> address;
}
