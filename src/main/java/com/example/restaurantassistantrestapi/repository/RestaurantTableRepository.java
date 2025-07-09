package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.RestaurantTable;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantTableRepository extends CrudRepository<RestaurantTable, Long> {
}
