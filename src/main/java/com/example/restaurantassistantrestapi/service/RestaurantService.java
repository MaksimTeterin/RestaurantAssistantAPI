package com.example.restaurantassistantrestapi.service;

import com.example.restaurantassistantrestapi.exception.ResourceNotFoundException;
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
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> getRestaurantById(int id) {
        return restaurantRepository.findById(id);
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(int id) {
        restaurantRepository.deleteById(id);
    }

    public String getRestaurantsDescriptionById(int id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        return restaurant.orElseThrow(() -> new ResourceNotFoundException("Restaurant not found")).getGeneralDescription();
    }

    public String getRestaurantDescriptionById(int id) {
        return restaurantRepository.findById(id).orElseThrow().getGeneralDescription();
    }

    public Restaurant updateRestaurant(int id, Restaurant restaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + id));

        existingRestaurant.setGeneralDescription(restaurant.getGeneralDescription());
        existingRestaurant.setAddress(restaurant.getAddress());
        existingRestaurant.setPhone(restaurant.getPhone());
        existingRestaurant.setName(restaurant.getName());
        existingRestaurant.setOwnerId(restaurant.getOwnerId());

        return restaurantRepository.save(existingRestaurant);

    }
}
