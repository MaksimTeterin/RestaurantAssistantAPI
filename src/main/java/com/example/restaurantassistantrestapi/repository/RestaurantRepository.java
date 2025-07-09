package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository  extends CrudRepository<Restaurant, Long> {
}
