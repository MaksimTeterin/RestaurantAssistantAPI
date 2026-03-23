package com.example.restaurantassistantrestapi.controller;

import com.example.restaurantassistantrestapi.model.User;
import com.example.restaurantassistantrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if(userService.userExistsByEmail(user.getEmail())) {
            return new ResponseEntity<>(user, HttpStatus.CONFLICT);
        }
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(
            "#id == authentication.principal.id or hasRole('SYSTEM_ADMIN')"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getChatId/{id}")
    @PreAuthorize(
            "#id == authentication.principal.id or hasRole('SYSTEM_ADMIN')"
    )
    public ResponseEntity<String> getChatId(@PathVariable int id) {
            return new ResponseEntity<>(userService.getUUIDByUserId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @GetMapping("/userExistsByEmail/{email}")
    public ResponseEntity<Boolean> userExistsByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.userExistsByEmail(email), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.CREATED);
    }
}
