package com.example.restaurantassistantrestapi.controller;

import com.example.restaurantassistantrestapi.model.Client;
import com.example.restaurantassistantrestapi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/me")
    public Client getMyData(@AuthenticationPrincipal Client client) {
        return client;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> getClientById(@PathVariable long id) {
        Optional<Client> client = clientService.getClientById(id);
        return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PreAuthorize("hasRole('RESTAURANT_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        if(clientService.clientExistsByEmail(client.getEmail())) {
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getChatId/{id}")
    public ResponseEntity<String> getChatId(@PathVariable long id) {
            return new ResponseEntity<>(clientService.getUUIDByClientId(id), HttpStatus.OK);
    }

    @GetMapping("/clientExistsByEmail/{email}")
    public ResponseEntity<Boolean> getChatId(@PathVariable String email) {
        return new ResponseEntity<>(clientService.clientExistsByEmail(email), HttpStatus.OK);
    }
}
