package com.example.restaurantassistantrestapi.ControllerTests;

import com.example.restaurantassistantrestapi.Config.Auth.JwtAuthenticationFilter;
import com.example.restaurantassistantrestapi.controller.HealthController;
import com.example.restaurantassistantrestapi.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(HealthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HealthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void shouldReturnApiIsRunning() throws Exception{
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/"));
        response.andExpect((result -> result.getResponse().getContentAsString().contains("API is running")));
    }
}
