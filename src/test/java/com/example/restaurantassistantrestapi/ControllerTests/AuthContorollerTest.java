package com.example.restaurantassistantrestapi.ControllerTests;

import com.example.restaurantassistantrestapi.DTOs.AuthRequestDTO;
import com.example.restaurantassistantrestapi.controller.AuthContoroller;
import com.example.restaurantassistantrestapi.model.User;
import com.example.restaurantassistantrestapi.service.HmacService;
import com.example.restaurantassistantrestapi.service.JwtService;
import com.example.restaurantassistantrestapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthContoroller.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthContorollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HmacService hmacService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldReturnTokenForValidRequest() throws Exception {
        AuthRequestDTO request = new AuthRequestDTO();
        request.email = "test@example.com";
        request.fullName = "Test User";
        request.timestamp = System.currentTimeMillis();
        request.signature = "validSignature";

        Mockito.when(hmacService.verifySignature(eq(request.email), eq(request.timestamp), eq(request.signature)))
                .thenReturn(true);
        Mockito.when(userService.userExistsByEmail(eq(request.email)))
                .thenReturn(true);
        Mockito.when(jwtService.generateUserToken(eq(request.email)))
                .thenReturn("mockedToken");

        String requestJson = """
                {
                    "email": "test@example.com",
                    "fullName": "Test User",
                    "timestamp": %d,
                    "signature": "validSignature"
                }
                """.formatted(request.timestamp);

        mockMvc.perform(post("/api/auth/getToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUnauthorizedForInvalidSignature() throws Exception {
        AuthRequestDTO request = new AuthRequestDTO();
        request.email = "test@example.com";
        request.fullName = "Test User";
        request.timestamp = System.currentTimeMillis();
        request.signature = "invalidSignature";

        Mockito.when(hmacService.verifySignature(eq(request.email), eq(request.timestamp), eq(request.signature)))
                .thenReturn(false);

        String requestJson = """
                {
                    "email": "test@example.com",
                    "fullName": "Test User",
                    "timestamp": %d,
                    "signature": "invalidSignature"
                }
                """.formatted(request.timestamp);

        mockMvc.perform(post("/api/auth/getToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid Signature"));
    }

    @Test
    void shouldReturnUnauthorizedForExpiredTimestamp() throws Exception {
        AuthRequestDTO request = new AuthRequestDTO();
        request.email = "test@example.com";
        request.fullName = "Test User";
        request.timestamp = System.currentTimeMillis() - (10 * 60 * 1000);
        request.signature = "validSignature";

        Mockito.when(hmacService.verifySignature(eq(request.email), eq(request.timestamp), eq(request.signature)))
                .thenReturn(true);

        String requestJson = """
                {
                    "email": "test@example.com",
                    "fullName": "Test User",
                    "timestamp": %d,
                    "signature": "validSignature"
                }
                """.formatted(request.timestamp);

        mockMvc.perform(post("/api/auth/getToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Timestamp expired"));
    }

    @Test
    void shouldCreateNewUserIfNotExists() throws Exception {
        AuthRequestDTO request = new AuthRequestDTO();
        request.email = "newuser@example.com";
        request.fullName = "New User";
        request.timestamp = System.currentTimeMillis();
        request.signature = "validSignature";

        Mockito.when(hmacService.verifySignature(eq(request.email), eq(request.timestamp), eq(request.signature)))
                .thenReturn(true);
        Mockito.when(userService.userExistsByEmail(eq(request.email)))
                .thenReturn(false);
        Mockito.when(jwtService.generateUserToken(eq(request.email)))
                .thenReturn("mockedToken");

        String requestJson = """
                {
                    "email": "newuser@example.com",
                    "fullName": "New User",
                    "timestamp": %d,
                    "signature": "validSignature"
                }
                """.formatted(request.timestamp);

        mockMvc.perform(post("/api/auth/getToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "token": "mockedToken"
                        }
                        """));

        Mockito.verify(userService).addUser(any(User.class));
    }
}