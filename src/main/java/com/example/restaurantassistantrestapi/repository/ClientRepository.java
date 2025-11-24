package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Optional<Client> findClientsByEmail(String email);

    Optional<Object> existsClientByEmail(String email);
}
