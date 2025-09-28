package com.example.restaurantassistantrestapi.service;

import com.example.restaurantassistantrestapi.model.Restaurant;
import com.example.restaurantassistantrestapi.model.RestaurantTable;
import com.example.restaurantassistantrestapi.repository.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantTableService {

    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public List<RestaurantTable> getAllRestaurantTables() {
        return (List<RestaurantTable>) restaurantTableRepository.findAll();
    }

    public Optional<RestaurantTable> getRestaurantTableById(long id) {
         return restaurantTableRepository.findById(id);
    }

    public RestaurantTable addRestaurantTable(RestaurantTable restaurantTable) {
        return restaurantTableRepository.save(restaurantTable);
    }

    public void deleteRestaurantTable(long id) {
        restaurantTableRepository.deleteById(id);
    }
}
