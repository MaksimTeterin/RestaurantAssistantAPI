package com.example.restaurantassistantrestapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

     @GetMapping("/")
    public String health() {
         return "API is running";
     }
}
