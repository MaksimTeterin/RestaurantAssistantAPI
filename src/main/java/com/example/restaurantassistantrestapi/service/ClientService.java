package com.example.restaurantassistantrestapi.service;

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
        Client client = clientRepository.findById(id).get();
        clientRepository.delete(client);
    }

    public String getUUIDByClientId(long id) {
        Client client = clientRepository.findById(id).get();
        return (client.getChatId()).toString();
    }

}
