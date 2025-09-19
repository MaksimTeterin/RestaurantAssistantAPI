package com.example.restaurantassistantrestapi.ControllerTests;

import com.example.restaurantassistantrestapi.controller.RestaurantController;
import com.example.restaurantassistantrestapi.model.Restaurant;
import com.example.restaurantassistantrestapi.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = RestaurantController.class)
public class RestaurantControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void RestaurantContorller_CreateRestaurant_ReturnCreated() throws Exception {
        Restaurant restaurant = Restaurant.builder().id(1).name("Test").build();

        given(restaurantService.addRestaurant(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/restaurants")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(restaurant)));

        response.andExpect((MockMvcResultMatchers.status().isCreated()));
    }

    @Test
    public void RestaurantController_GetRestaurantById_Return() throws Exception {
        int restaurantId = 1;
        Restaurant restaurant = Restaurant.builder().id(restaurantId).build();

        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(restaurant);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants/" + restaurantId));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void RestaurantController_GetAllRestaurants_Return() throws Exception {
        Restaurant restaurant = Restaurant.builder().build();

        when(restaurantService.getAllRestaurants()).thenReturn(List.of(restaurant));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void RestaurantController_DeleteRestaurant_ReturnString() throws Exception {
        int restaurantId = 1;

        doNothing().when(restaurantService).deleteRestaurant(restaurantId);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/restaurants/" + restaurantId));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void RestaurantController_GetRestaurantDescription_Return() throws Exception {
        Restaurant restaurant = Restaurant.builder().generalDescription("Description").id(1).build();

        when(restaurantService.getRestaurantsDescriptionById(restaurant.getId())).thenReturn(restaurant.getGeneralDescription());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants/restaurantsDescription/" + restaurant.getId()));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }
}
