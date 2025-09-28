package com.example.restaurantassistantrestapi.service;

import com.example.restaurantassistantrestapi.model.Restaurant;
import com.example.restaurantassistantrestapi.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        return (List<Restaurant>) restaurantRepository.findAll();
    }

    public Optional<Restaurant> getRestaurantById(long id) {
        return restaurantRepository.findById(id);
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(long id) {
        restaurantRepository.deleteById(id);
    }

    public String getRestaurantsDescriptionById(long id) {
        Restaurant restaurant = restaurantRepository.findFirstById(id);
        return restaurant.getGeneralDescription();
    }
}
