package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository  extends JpaRepository<Restaurant, Integer> {
}
