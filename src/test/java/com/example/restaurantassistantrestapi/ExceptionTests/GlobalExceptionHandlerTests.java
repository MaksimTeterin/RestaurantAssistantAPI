package com.example.restaurantassistantrestapi.ExceptionTests;

import com.example.restaurantassistantrestapi.exception.ErrorResponse;
import com.example.restaurantassistantrestapi.exception.GlobalExceptionHandler;
import com.example.restaurantassistantrestapi.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTests {

    @InjectMocks
    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testResourceNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Restaurant not found");
        ResponseEntity<ErrorResponse> response = handler.resourceNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Restaurant not found", response.getBody().getMessage());
        assertEquals("Resource not found", response.getBody().getDetails());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void testHandleNotValidException() {
        Exception ex = new MissingServletRequestParameterException("id", "String");
        ResponseEntity<ErrorResponse> response = handler.handleNotValidException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request", response.getBody().getMessage());
        assertTrue(response.getBody().getDetails().contains("id"));
    }

    @Test
    void testHandleGenericException() {
        Exception ex = new RuntimeException("Unexpected failure");
        ResponseEntity<ErrorResponse> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred.", response.getBody().getMessage());
        assertTrue(response.getBody().getDetails().contains("RuntimeException"));
    }

}
