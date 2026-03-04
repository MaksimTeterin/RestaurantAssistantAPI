package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Integer> {
}
