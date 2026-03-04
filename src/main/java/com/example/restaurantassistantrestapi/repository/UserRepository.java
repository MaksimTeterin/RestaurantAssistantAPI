package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUsersByEmail(String email);

    Boolean existsUserByEmail(String email);

    User findFirstByEmail(String email);
}
