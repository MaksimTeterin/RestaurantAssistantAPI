package com.example.restaurantassistantrestapi.RepositoryTests;

import com.example.restaurantassistantrestapi.model.RestaurantTable;
import com.example.restaurantassistantrestapi.repository.RestaurantTableRepository;
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
public class RestaurantTableRepositoryTests {

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Test
    public void RestaurantTableRepository_SaveAll_ReturnsSavedRestaurantTable() {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable();

        //Act
        RestaurantTable savedRestaurantTable = restaurantTableRepository.save(restaurantTable);

        //Assert
        Assertions.assertThat(savedRestaurantTable).isNotNull();
        Assertions.assertThat(savedRestaurantTable.getId()).isGreaterThan(0);
    }

    @Test
    public void RestaurantTableRepository_FindAll_ReturnsMoreThanOneRestaurantTable() {
        RestaurantTable restaurantTable = new RestaurantTable();
        RestaurantTable restaurantTable1 = new RestaurantTable();

        restaurantTableRepository.save(restaurantTable);
        restaurantTableRepository.save(restaurantTable1);

        List<RestaurantTable> restaurantTables = (List<RestaurantTable>) restaurantTableRepository.findAll();

        Assertions.assertThat(restaurantTables.size()).isEqualTo(2);
        Assertions.assertThat(restaurantTables).isNotNull();
    }

    @Test
    public void RestaurantTableRepository_FindById_ReturnsRestaurantTable() {
        RestaurantTable restaurantTable = new RestaurantTable();

        restaurantTableRepository.save(restaurantTable);

        RestaurantTable restaurantTableFoundById = restaurantTableRepository.findById((long) restaurantTable.getId()).get();

        Assertions.assertThat(restaurantTableFoundById).isNotNull();
    }

    @Test
    public void RestaurantTableRepository_DeleteById_ReturnsRestaurantTable() {
        RestaurantTable restaurantTable = new RestaurantTable();

        restaurantTableRepository.save(restaurantTable);

        restaurantTableRepository.deleteById((long) restaurantTable.getId());
        Optional<RestaurantTable> restaurantTableReturned = restaurantTableRepository.findById((long) restaurantTable.getId());

        Assertions.assertThat(restaurantTableReturned).isEmpty();
    }

    @Test
    public void RestaurantTableRepository_save_ReturnsSavedRestaurantTable() {
        RestaurantTable restaurantTable = new RestaurantTable();

        restaurantTableRepository.save(restaurantTable);

        Assertions.assertThat(restaurantTable).isNotNull();
        Assertions.assertThat(restaurantTable.getId()).isGreaterThanOrEqualTo(0);
    }
}