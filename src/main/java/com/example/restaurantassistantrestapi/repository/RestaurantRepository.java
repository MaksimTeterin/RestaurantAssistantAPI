package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RestaurantRepository  extends CrudRepository<Restaurant, Long> {
    Optional<Restaurant> getRestaurantById(long id);

    Restaurant findFirstById(long id);
}
