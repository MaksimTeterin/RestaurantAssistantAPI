package com.example.restaurantassistantrestapi.RepositoryTests;

import com.example.restaurantassistantrestapi.model.User;
import com.example.restaurantassistantrestapi.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {
    
    @Autowired
    private UserRepository userRepository;

    @Test
    public void ClientRepository_SaveAll_ReturnsSavedClient() {
        //Arrange
        User user = new User();

        //Act
        User savedUser = userRepository.save(user);

        //Assert
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

//    @Test
//    public void ClientRepository_FindAll_ReturnsMoreThanOneClient() {
//        Client client = new Client();
//        Client client1 = new Client();
//
//        clientRepository.save(client);
//        clientRepository.save(client1);
//
//        List<Client> clients = (List<Client>) clientRepository.findAll();
//
//        Assertions.assertThat(clients.size()).isEqualTo(2);
//        Assertions.assertThat(clients).isNotNull();
//    }

    @Test
    public void ClientRepository_FindById_ReturnsClient() {
        User user = new User();

        userRepository.save(user);

        User userFoundById = userRepository.findById((long) user.getId()).get();

        Assertions.assertThat(userFoundById).isNotNull();
    }

    @Test
    public void ClientRepository_DeleteById_ReturnsClient() {
        User user = new User();

        userRepository.save(user);

        userRepository.deleteById((long) user.getId());
        Optional<User> clientReturned = userRepository.findById((long) user.getId());

        Assertions.assertThat(clientReturned).isEmpty();
    }

    @Test
    public void ClientRepository_save_ReturnsSavedClient() {
        User user = new User();

        userRepository.save(user);

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isGreaterThanOrEqualTo(0);
    }
}
