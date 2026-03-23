package com.example.restaurantassistantrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRoles userRoles = UserRoles.ROLE_USER;
    private UUID chatId = UUID.randomUUID();

    public User(String email, String fullName) { // Constructor to create new Clien entity, when user wants to getToken for the first time
        String[] splittedName = fullName.split(" ");
        this.firstName = splittedName[0];
        if(splittedName.length > 1 && Arrays.stream(splittedName).anyMatch(e -> splittedName[splittedName.length - 1].startsWith("("))) {
            this.lastName = splittedName[1];
        } else {
            lastName = splittedName[splittedName.length - 1];
        }
        this.email = email;
        this.userRoles = UserRoles.ROLE_USER;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRoles.name()));
    }


    @Override
    @JsonIgnore
    public String getPassword() {
        return "test_password";
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return "test_username";
    }
}
