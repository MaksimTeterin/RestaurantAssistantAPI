package com.example.restaurantassistantrestapi.RepositoryTests;

import com.example.restaurantassistantrestapi.model.Client;
import com.example.restaurantassistantrestapi.repository.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ClientRepositoryTests {
    
    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void ClientRepository_SaveAll_ReturnsSavedClient() {
        //Arrange
        Client client = new Client();

        //Act
        Client savedClient = clientRepository.save(client);

        //Assert
        Assertions.assertThat(savedClient).isNotNull();
        Assertions.assertThat(savedClient.getId()).isGreaterThan(0);
    }

    @Test
    public void ClientRepository_FindAll_ReturnsMoreThanOneClient() {
        Client client = new Client();
        Client client1 = new Client();

        clientRepository.save(client);
        clientRepository.save(client1);

        List<Client> clients = (List<Client>) clientRepository.findAll();

        Assertions.assertThat(clients.size()).isEqualTo(2);
        Assertions.assertThat(clients).isNotNull();
    }

    @Test
    public void ClientRepository_FindById_ReturnsClient() {
        Client client = new Client();

        clientRepository.save(client);

        Client clientFoundById = clientRepository.findById((long) client.getId()).get();

        Assertions.assertThat(clientFoundById).isNotNull();
    }

    @Test
    public void ClientRepository_DeleteById_ReturnsClient() {
        Client client = new Client();

        clientRepository.save(client);

        clientRepository.deleteById((long) client.getId());
        Optional<Client> clientReturned = clientRepository.findById((long) client.getId());

        Assertions.assertThat(clientReturned).isEmpty();
    }

    @Test
    public void ClientRepository_save_ReturnsSavedClient() {
        Client client = new Client();

        clientRepository.save(client);

        Assertions.assertThat(client).isNotNull();
        Assertions.assertThat(client.getId()).isGreaterThanOrEqualTo(0);
    }
}
