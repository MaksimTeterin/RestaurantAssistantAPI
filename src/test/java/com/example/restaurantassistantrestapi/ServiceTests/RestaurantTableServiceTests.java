package com.example.restaurantassistantrestapi.ServiceTests;

import com.example.restaurantassistantrestapi.model.RestaurantTable;
import com.example.restaurantassistantrestapi.repository.RestaurantTableRepository;
import com.example.restaurantassistantrestapi.service.RestaurantTableService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestaurantTableServiceTests {

    @Mock
    private RestaurantTableRepository restaurantTableRepository;

    @InjectMocks
    private RestaurantTableService restaurantTableService;

    @Test
    public void RestaurantTableService_AddRestaurantTable_ReturnsRestaurantTable() {
        RestaurantTable restaurantTable = new RestaurantTable();

        when(restaurantTableRepository.save(Mockito.any(RestaurantTable.class))).thenReturn(restaurantTable);

        RestaurantTable savedRestaurantTable = restaurantTableService.addRestaurantTable(restaurantTable);

        Assertions.assertThat(savedRestaurantTable).isNotNull();
    }

//    @Test
//    public void RestaurantTableService_getAllRestaurantTables_ReturnsAllRestaurantTables() {
//        List<RestaurantTable> restaurantTables = new ArrayList<>();
//
//        when(restaurantTableRepository.findAll(Mockito.any(RestaurantTable.class)).thenReturn(restaurantTables));
//    }

    @Test
    public void RestaurantTableService_getRestaurantTableById_ReturnsRestaurantTable() {
        RestaurantTable restaurantTable = new RestaurantTable();

        when(restaurantTableRepository.findById((long) restaurantTable.getId())).thenReturn(Optional.of(restaurantTable));

        Optional<RestaurantTable> savedRestaurantTable = restaurantTableService.getRestaurantTableById((long) restaurantTable.getId());

        Assertions.assertThat(savedRestaurantTable).isNotNull();
    }
}
