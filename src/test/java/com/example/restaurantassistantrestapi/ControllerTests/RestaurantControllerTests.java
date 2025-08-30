package com.example.restaurantassistantrestapi.ControllerTests;

import com.example.restaurantassistantrestapi.controller.RestaurantController;
import com.example.restaurantassistantrestapi.model.Restaurant;
import com.example.restaurantassistantrestapi.model.RestaurantTable;
import com.example.restaurantassistantrestapi.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = RestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RestaurantControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestaurantService service;

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private RestaurantService restaurantService;


    @Test
    public void RestaurantContorller_CreateRestaurant_ReturnCreated() throws Exception {
        Restaurant restaurant = Restaurant.builder().id(1).name("Test").build();

        given(service.addRestaurant(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/restaurants")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(restaurant)));

        response.andExpect((MockMvcResultMatchers.status().isCreated()));
    }

    @Test
    public void RestaurantController_GetRestaurantById_Return() throws Exception {
        int restaurantId = 1;
        Restaurant restaurant = Restaurant.builder().id(restaurantId).build();

        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(Optional.of(restaurant));

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

        doNothing().when(service).deleteRestaurant(restaurantId);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/restaurants/" + restaurantId));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
