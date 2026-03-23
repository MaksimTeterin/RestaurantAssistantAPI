package com.example.restaurantassistantrestapi.ServiceTests;

import com.example.restaurantassistantrestapi.model.User;
import com.example.restaurantassistantrestapi.repository.UserRepository;
import com.example.restaurantassistantrestapi.service.UserService;
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
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void getAllClients_shouldReturnAllClients() {
        User user1 = new User();
        User user2 = new User();
        List<User> list = Arrays.asList(user2, user1);

        when(userRepository.findAll()).thenReturn(list);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(list, result);
    }

    @Test
    public void getClientById_shouldReturnClient() {
        User user = new User();
        user.setId(1);

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(user.getId());

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void getClientById_shouldReturnNullWhenClientNotFound() {

        when(userRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(1);

        assertFalse(result.isPresent());

    }

    @Test
    public void addClient_shouldAddClient_and_ReturnCreatedClient(){
        User user = new User();

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addUser(user);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    public void deleteClient_shouldDeleteClient_and_ReturnNothing(){
        User user = new User();
        user.setId(1);

        doNothing().when(userRepository).deleteById(user.getId());

        userService.deleteUser(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void getUUIDByClientId_shouldReturnClientUUID(){
        User user = new User();
        user.setId(1);

        when(userRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(user));

        String result = userService.getUUIDByUserId(user.getId());

        assertNotNull(result);
    }

    @Test
    public void userExistsByEmail_shouldReturnTrueWhenUserExists() {
        String email = "test@example.com";

        when(userRepository.existsUserByEmail(email)).thenReturn(true);

        boolean result = userService.userExistsByEmail(email);

        assertTrue(result);
    }

}
