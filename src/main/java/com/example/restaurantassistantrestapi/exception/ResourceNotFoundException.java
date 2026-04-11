package com.example.restaurantassistantrestapi.exception;


import lombok.Getter;


@Getter
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
        System.out.println(message);
    }
}
