package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUsersByEmail(String email);

    Boolean existsUserByEmail(String email);

    User findFirstByEmail(String email);
}
