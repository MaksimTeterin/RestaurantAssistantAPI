package com.example.restaurantassistantrestapi.ControllerTests;

import com.example.restaurantassistantrestapi.Config.Auth.JwtAuthenticationFilter;
import com.example.restaurantassistantrestapi.Config.NoSecurityConfig;
import com.example.restaurantassistantrestapi.controller.UserController;
import com.example.restaurantassistantrestapi.model.User;
import com.example.restaurantassistantrestapi.model.UserRoles;
import com.example.restaurantassistantrestapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Test
    void getAllUsers_shouldReturnAllUsers() throws Exception {
        List<User> users = List.of(User.builder().userRoles(UserRoles.ROLE_USER).id(1L).build());
        when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")).andExpect(status().isOk());
    }

    @Test
    void getUserById_shouldReturnFoundUserIfItExists() throws Exception {
        User user = User.builder().userRoles(UserRoles.ROLE_USER).id(1L).build();
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1")).andExpect(status().isOk());
    }

    @Test
    void getUserById_shouldReturnNotFoundIfItDoesNotExist() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1")).andExpect(status().isNotFound());
    }

    @Test
    void createUser_shouldReturnOkIfUserDoesNotExist() throws Exception {
        User user = User.builder().userRoles(UserRoles.ROLE_USER).id(1L).build();
        given(userService.addUser(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void createUser_shouldReturnConflictIfUserAlreadyExists() throws Exception {
        User user = User.builder().userRoles(UserRoles.ROLE_USER).email("test@test.com").id(1L).build();
        when(userService.userExistsByEmail(user.getEmail())).thenReturn(true);
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isConflict());
    }

        @Test
        void deleteUser_shouldReturnNoContentForExistingUser() throws Exception {
            doNothing().when(userService).deleteUser(1L);
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1"))
                    .andExpect(status().isNoContent());
        }

        @Test
        void deleteUser_shouldReturnNotFoundForNonExistentUser() throws Exception {
            doNothing().when(userService).deleteUser(1L);
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/999"))
                    .andExpect(status().isNoContent());
        }
    }
