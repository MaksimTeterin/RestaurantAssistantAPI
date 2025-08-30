package com.example.restaurantassistantrestapi.RepositoryTests;

import com.example.restaurantassistantrestapi.model.Restaurant;
import com.example.restaurantassistantrestapi.repository.RestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RestaurantRepositoryTests {
    
    @Autowired
    private RestaurantRepository restaurantRepository;
    
    @Test
    public void RestaurantRepository_SaveAll_ReturnsSavedRestaurant() {
        //Arrange
        Restaurant restaurant = new Restaurant();

        //Act
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        //Assert
        Assertions.assertThat(savedRestaurant).isNotNull();
        Assertions.assertThat(savedRestaurant.getId()).isGreaterThan(0);
    }

    @Test
    public void RestaurantRepository_FindAll_ReturnsMoreThanOneRestaurant() {
        Restaurant restaurant = new Restaurant();
        Restaurant restaurant1 = new Restaurant();

        restaurantRepository.save(restaurant);
        restaurantRepository.save(restaurant1);

        List<Restaurant> restaurants = (List<Restaurant>) restaurantRepository.findAll();

        Assertions.assertThat(restaurants.size()).isEqualTo(2);
        Assertions.assertThat(restaurants).isNotNull();
    }

    @Test
    public void RestaurantRepository_FindById_ReturnsRestaurant() {
        Restaurant restaurant = new Restaurant();

        restaurantRepository.save(restaurant);

        Restaurant restaurantFoundById = restaurantRepository.findById((long) restaurant.getId()).get();

        Assertions.assertThat(restaurantFoundById).isNotNull();
    }

    @Test
    public void RestaurantRepository_DeleteById_ReturnsRestaurant() {
        Restaurant restaurant = new Restaurant();

        restaurantRepository.save(restaurant);

        restaurantRepository.deleteById((long) restaurant.getId());
        Optional<Restaurant> restaurantReturned = restaurantRepository.findById((long) restaurant.getId());

        Assertions.assertThat(restaurantReturned).isEmpty();
    }

    @Test
    public void RestaurantRepository_save_ReturnsSavedRestaurant() {
        Restaurant restaurant = new Restaurant();

        restaurantRepository.save(restaurant);

        Assertions.assertThat(restaurant).isNotNull();
        Assertions.assertThat(restaurant.getId()).isGreaterThanOrEqualTo(0);
    }
}
