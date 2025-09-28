package com.example.restaurantassistantrestapi.exception;


import lombok.Builder;
import lombok.Getter;


@Getter
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
