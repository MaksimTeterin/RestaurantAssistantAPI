package com.example.restaurantassistantrestapi.ControllerTests;

import com.example.restaurantassistantrestapi.controller.ClientController;
import com.example.restaurantassistantrestapi.controller.ClientController;
import com.example.restaurantassistantrestapi.model.Client;
import com.example.restaurantassistantrestapi.service.ClientService;
import com.example.restaurantassistantrestapi.controller.ClientController;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ClientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService service;

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private ClientService clientService;


    @Test
    public void ClientContorller_CreateClient_ReturnCreated() throws Exception {
        Client client = Client.builder().id(1).build();

        given(service.addClient(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/clients")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(client)));

        response.andExpect((MockMvcResultMatchers.status().isCreated()));
    }

    @Test
    public void ClientController_GetClientById_Return() throws Exception {
        int clientId = 1;
        Client client = Client.builder().id(clientId).build();

        when(clientService.getClientById(clientId)).thenReturn(Optional.of(client));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/" + clientId));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ClientController_GetAllClients_Return() throws Exception {
        Client client = Client.builder().build();

        when(clientService.getAllClients()).thenReturn(List.of(client));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/clients"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ClientController_DeleteClient_ReturnString() throws Exception {
        int clientId = 1;

        doNothing().when(service).deleteClient(clientId);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients/" + clientId));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
