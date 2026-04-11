package com.example.restaurantassistantrestapi.service;

import com.example.restaurantassistantrestapi.exception.ResourceNotFoundException;
import com.example.restaurantassistantrestapi.model.RestaurantTable;
import com.example.restaurantassistantrestapi.repository.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantTableService {

    private final RestaurantTableRepository restaurantTableRepository;
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository, RestaurantService restaurantService) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.restaurantService = restaurantService;
    }

    public List<RestaurantTable> getAllRestaurantTables() {
        return restaurantTableRepository.findAll();
    }

    public Optional<RestaurantTable> getRestaurantTableById(int id) {
         return restaurantTableRepository.findById(id);
    }

    public RestaurantTable addRestaurantTable(RestaurantTable restaurantTable) {
        return restaurantTableRepository.save(restaurantTable);
    }

    public void deleteRestaurantTable(int id) {
        restaurantTableRepository.deleteById(id);
    }

    public List<RestaurantTable> getRestaurantTablesByRestaurantId(int restaurantId) {
        if(restaurantService.getRestaurantById(restaurantId).isPresent()){
            return restaurantTableRepository.getRestaurantTablesByRestaurantId(restaurantId);
        }
        throw new ResourceNotFoundException("Restaurant not found");
    }

    public RestaurantTable updateRestaurantTable(int id, RestaurantTable restaurantTable) {
        RestaurantTable existingRestaurantTable = getRestaurantTableById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant table not found with ID: " + id));
        existingRestaurantTable.setCapacity(restaurantTable.getCapacity());
        return restaurantTableRepository.save(existingRestaurantTable);
    }

    public List<RestaurantTable> findAvailableTablesByCapacityAndRestaurantId(int restaurantId, int guestNumber) {
        return restaurantTableRepository.findAllByCapacityIsGreaterThanEqualAndRestaurantId(guestNumber, restaurantId);
    }

    public List<RestaurantTable> findAvailableTables(int restaurantId, int guestNumber) {
        return restaurantTableRepository.findAllByCapacityIsGreaterThanEqualAndRestaurantId(guestNumber, restaurantId);
    }
}
