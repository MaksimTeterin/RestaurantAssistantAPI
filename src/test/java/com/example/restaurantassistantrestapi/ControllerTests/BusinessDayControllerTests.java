package com.example.restaurantassistantrestapi.ControllerTests;

import com.example.restaurantassistantrestapi.controller.BusinessDayController;
import com.example.restaurantassistantrestapi.model.BusinessDay;
import com.example.restaurantassistantrestapi.service.BusinessDayService;
import com.example.restaurantassistantrestapi.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = BusinessDayController.class)
public class BusinessDayControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BusinessDayService businessDayService;

    @Test
    public void BusinessDayContoller_getBusinessDays_ReturnBusinessDayList() throws Exception {
        BusinessDay businessDay = BusinessDay.builder().build();

        when(businessDayService.getBusinessDays()).thenReturn(List.of(businessDay));

        ResultActions response  = mockMvc.perform(MockMvcRequestBuilders.get("/api/businessdays"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void BusinessDayController_CreateBusinessDay_ReturnCreated() throws Exception {
        BusinessDay businessDay = BusinessDay.builder().id(1).build();

        given(businessDayService.addBusinessDay(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/businessdays")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(businessDay))
                );

        response.andExpect((MockMvcResultMatchers.status().isCreated()));
    }

    @Test
    public void BusinessDayController_GetBusinessDayById_Return() throws Exception {
        BusinessDay businessDay = BusinessDay.builder().id(1).build();

        when(businessDayService.getBusinessDayById(businessDay.getId())).thenReturn(Optional.of(businessDay));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/businessdays/" + businessDay.getId()));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void BusinessDayController_DeleteBusinessDay_RetrunString() throws Exception {
        BusinessDay businessDay = BusinessDay.builder().id(1).build();

        doNothing().when(businessDayService).deleteBusinessDay(businessDay.getId());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/businessdays/" + businessDay.getId()));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
