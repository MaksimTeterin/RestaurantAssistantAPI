package com.example.restaurantassistantrestapi.service;

import com.example.restaurantassistantrestapi.exception.ResourceNotFoundException;
import com.example.restaurantassistantrestapi.model.Client;
import com.example.restaurantassistantrestapi.repository.ClientRepository;
import com.example.restaurantassistantrestapi.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return (List<Client>) clientRepository.findAll();
    }

    public Optional<Client> getClientById(long id) {
        return clientRepository.findById(id);
    }

    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(long id) {
        clientRepository.deleteById(id);
    }

    public String getUUIDByClientId(long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + id));
        return (client.getChatId()).toString();
    }

    public boolean clientExistsByEmail(String email) {
        return clientRepository.existsClientByEmail(email);
    }

    public Client getClientByEmail(String email) {
        Client client = clientRepository.findFirstByEmail(email);
        return (client);
    }

}
