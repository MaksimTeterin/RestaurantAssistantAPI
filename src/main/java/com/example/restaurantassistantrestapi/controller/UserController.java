package com.example.restaurantassistantrestapi.controller;

import com.example.restaurantassistantrestapi.model.User;
import com.example.restaurantassistantrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if(userService.userExistsByEmail(user.getEmail())) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable long id, @AuthenticationPrincipal User authenticatedUser) {
        if(authenticatedUser.getId() != id){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getChatId/{id}")
    public ResponseEntity<String> getChatId(@PathVariable long id, @AuthenticationPrincipal User authenticatedUser) {
        if(authenticatedUser.getId() != id){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
            return new ResponseEntity<>(userService.getUUIDByUserId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @GetMapping("/userExistsByEmail/{email}")
    public ResponseEntity<Boolean> getChatId(@PathVariable String email) {
        return new ResponseEntity<>(userService.userExistsByEmail(email), HttpStatus.OK);
    }
}
