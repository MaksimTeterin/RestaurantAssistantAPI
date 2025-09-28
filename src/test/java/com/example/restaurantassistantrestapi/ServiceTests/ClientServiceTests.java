package com.example.restaurantassistantrestapi.ServiceTests;

import com.example.restaurantassistantrestapi.model.Client;
import com.example.restaurantassistantrestapi.repository.ClientRepository;
import com.example.restaurantassistantrestapi.service.ClientService;
import com.example.restaurantassistantrestapi.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void getAllClients_shouldReturnAllClients() {
        Client client1 = new Client();
        Client client2 = new Client();
        List<Client> list = Arrays.asList(client2, client1);

        when(clientRepository.findAll()).thenReturn(list);

        List<Client> result = clientService.getAllClients();

        assertNotNull(result);
        assertEquals(list, result);
    }

    @Test
    public void getClientById_shouldReturnClient() {
        Client client = new Client();
        client.setId(1L);

        when(clientRepository.findById(client.getId()))
                .thenReturn(Optional.of(client));

        Optional<Client> result = clientService.getClientById(client.getId());

        assertTrue(result.isPresent());
        assertEquals(client, result.get());
    }

    @Test
    public void getClientById_shouldReturnNullWhenClientNotFound() {

        when(clientRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Optional<Client> result = clientService.getClientById(1L);

        assertFalse(result.isPresent());

    }

    @Test
    public void addClient_shouldAddClient_and_ReturnCreatedClient(){
        Client client = new Client();

        when(clientRepository.save(client)).thenReturn(client);

        Client result = clientService.addClient(client);

        assertNotNull(result);
        assertEquals(client, result);
    }

    @Test
    public void deleteClient_shouldDeleteClient_and_ReturnNothing(){
        Client client = new Client();
        client.setId(1L);

        doNothing().when(clientRepository).deleteById(client.getId());

        clientService.deleteClient(client.getId());

        verify(clientRepository, times(1)).deleteById(client.getId());
    }

    @Test
    public void getUUIDByClientId_shouldReturnClientUUID(){
        Client client = new Client();
        client.setId(1L);

        when(clientRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(client));

        String result = clientService.getUUIDByClientId(client.getId());

        assertNotNull(result);
    }

}
