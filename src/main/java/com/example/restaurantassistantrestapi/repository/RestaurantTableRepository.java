package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Integer> {
    List<RestaurantTable> getRestaurantTablesByRestaurantId(int restaurantId);

    List<RestaurantTable> findAllByCapacityIsGreaterThanEqualAndRestaurantId(int guestNumber, int restaurantId);
}
