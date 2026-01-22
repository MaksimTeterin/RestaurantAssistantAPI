package com.example.restaurantassistantrestapi.controller;

import com.example.restaurantassistantrestapi.DTOs.AuthRequestDTO;
import com.example.restaurantassistantrestapi.model.User;
import com.example.restaurantassistantrestapi.service.UserService;
import com.example.restaurantassistantrestapi.service.HmacService;
import com.example.restaurantassistantrestapi.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthContoroller {

    private UserService userService;

    private final HmacService hmacService;
    private final JwtService jwtService;

    public AuthContoroller(HmacService hmacService, JwtService jwtService, UserService userService) {
        this.hmacService = hmacService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/getToken")
    public ResponseEntity<?> getToken(@RequestBody AuthRequestDTO req){

        System.out.println("getToken function called for email: " + req.email);

        if(!hmacService.verifySignature(req.email, req.timestamp, req.signature)){
            return ResponseEntity.status(401).body("Invalid Signature");
        }

        long now = System.currentTimeMillis();

        if (Math.abs(now - req.timestamp) >= 5 * 60 * 1000) {
            return ResponseEntity.status(401).body("Timestamp expired");
        }

        if(!userService.userExistsByEmail(req.email)){
            System.out.println("User with email: " + req.email + " does not exist, creating a new one");
            userService.addUser(new User(req.email, req.fullName));
            System.out.println("User with email: " + req.email + " created");
        } else System.out.println("User with email: " + req.email + " already exists");

        String token = jwtService.generateUserToken(req.email);

        System.out.println("Token given to:" + req.email);
        System.out.println("Token " + token);

        return ResponseEntity.ok(Map.of("token", token));
    }

}
