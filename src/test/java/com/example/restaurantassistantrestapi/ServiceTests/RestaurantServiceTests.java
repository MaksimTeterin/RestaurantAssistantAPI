package com.example.restaurantassistantrestapi.ServiceTests;

import com.example.restaurantassistantrestapi.model.Restaurant;
import com.example.restaurantassistantrestapi.repository.RestaurantRepository;
import com.example.restaurantassistantrestapi.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTests {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    public void getAllRestaurants_shouldReturnAllRestaurants() {
        Restaurant restaurant1 = new Restaurant();
        Restaurant restaurant2 = new Restaurant();
        List<Restaurant> list = Arrays.asList(restaurant2, restaurant1);

        when(restaurantRepository.findAll()).thenReturn(list);

        List<Restaurant> result = restaurantService.getAllRestaurants();

        assertNotNull(result);
        assertEquals(list, result);
    }

    @Test
    public void getRestaurantById_shouldReturnRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        when(restaurantRepository.findById(restaurant.getId()))
                .thenReturn(Optional.of(restaurant));

        Optional<Restaurant> result = restaurantService.getRestaurantById(restaurant.getId());

        assertTrue(result.isPresent());
        assertEquals(restaurant, result.get());
    }

    @Test
    public void getRestaurantById_shouldReturnNullWhenRestaurantNotFound() {

        when(restaurantRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Optional<Restaurant> result = restaurantService.getRestaurantById(1L);

        assertFalse(result.isPresent());

    }

    @Test
    public void addRestaurant_shouldAddRestaurant_and_ReturnCreatedRestaurant(){
        Restaurant restaurant = new Restaurant();

        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        Restaurant result = restaurantService.addRestaurant(restaurant);

        assertNotNull(result);
        assertEquals(restaurant, result);
    }

    @Test
    public void deleteRestaurant_shouldDeleteRestaurant_and_ReturnNothing(){
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        doNothing().when(restaurantRepository).deleteById(restaurant.getId());

        restaurantService.deleteRestaurant(restaurant.getId());

        verify(restaurantRepository, times(1)).deleteById(restaurant.getId());
    }

    @Test
    public void getRestaurantsDescriptionById_shouldReturnRestaurantsDescription(){
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setGeneralDescription("GeneralDescription");

        when(restaurantRepository.findFirstById(restaurant.getId())).thenReturn(restaurant);

        String result = restaurantService.getRestaurantsDescriptionById(restaurant.getId());

        assertNotNull(result);
        assertEquals(restaurant.getGeneralDescription(), result);
    }

}
