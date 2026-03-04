package com.example.restaurantassistantrestapi.service;

import com.example.restaurantassistantrestapi.exception.ResourceNotFoundException;
import com.example.restaurantassistantrestapi.model.User;
import com.example.restaurantassistantrestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public String getUUIDByUserId(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return (user.getChatId()).toString();
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public User getUserByEmail(String email) {
        return (userRepository.findFirstByEmail(email));
    }

}
