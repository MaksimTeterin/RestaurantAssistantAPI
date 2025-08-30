package com.example.restaurantassistantrestapi.ControllerTests;

import com.example.restaurantassistantrestapi.controller.RestaurantController;
import com.example.restaurantassistantrestapi.controller.RestaurantTableController;
import com.example.restaurantassistantrestapi.model.Restaurant;
import com.example.restaurantassistantrestapi.model.RestaurantTable;
import com.example.restaurantassistantrestapi.service.RestaurantService;
import com.example.restaurantassistantrestapi.service.RestaurantTableService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = RestaurantTableController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RestaurantTableControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestaurantTableService service;

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestaurantTableService restaurantTableService;

    @Test
    public void RestaurantTableController_CreateRestaurantTable_ReturnCreated() throws Exception {
        RestaurantTable restaurantTable = RestaurantTable.builder().build();

        given(service.addRestaurantTable(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/restauranttables")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(restaurantTable)));

        response.andExpect((MockMvcResultMatchers.status().isCreated()));
    }

    @Test
    public void RestaurantTableController_GetRestaurantTableById_Return() throws Exception {
        int restaurantTableId = 1;
        RestaurantTable restaurantTable = RestaurantTable.builder().id(restaurantTableId).build();

        when(restaurantTableService.getRestaurantTableById(restaurantTableId)).thenReturn(Optional.of(restaurantTable));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/restauranttables/" + restaurantTableId));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void RestaurantTableController_GetAllRestaurantTables_Return() throws Exception {
        RestaurantTable restaurantTable = RestaurantTable.builder().build();

        when(restaurantTableService.getAllRestaurantTables()).thenReturn(List.of(restaurantTable));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/restauranttables"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void RestaurantTableController_DeleteRestaurantTable_Return() throws Exception {
        int restaurantTableId = 1;

        doNothing().when(service).deleteRestaurantTable(restaurantTableId);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/restauranttables/" + restaurantTableId));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
