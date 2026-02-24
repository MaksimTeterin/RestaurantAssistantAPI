package com.example.restaurantassistantrestapi.ControllerTests;
import com.example.restaurantassistantrestapi.Config.Auth.JwtAuthenticationFilter;
import com.example.restaurantassistantrestapi.Config.SecurityConfig;
import com.example.restaurantassistantrestapi.controller.BookingController;
import com.example.restaurantassistantrestapi.model.Booking;
import com.example.restaurantassistantrestapi.model.User;
import com.example.restaurantassistantrestapi.model.UserRoles;
import com.example.restaurantassistantrestapi.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Optional;

import static com.example.restaurantassistantrestapi.model.UserRoles.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService service;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    Booking booking = Booking.builder().id(1L).build();

    @Test
    void getAllBookings_shouldReturnAllBookings() throws Exception {

        when(bookingService.getAllBookings()).thenReturn(List.of(booking));

        mockMvc.perform(get("/api/bookings")).andExpect(status().isOk());
    }

    @Test
    void createBooking_shouldReturn201() throws Exception {
        Booking booking = Booking.builder().id(2L).build();

        when(bookingService.addBooking(any(Booking.class))).thenReturn(booking);

        mockMvc.perform(post("/api/bookings")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(booking)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void getBookingById_shouldReturnBooking() throws Exception {
        when(bookingService.getBookingById(1L)).thenReturn(Optional.of(booking));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookings/1")).andExpect(status().isOk());

    }

    @Test
    void deleteBooking_shouldReturn204() throws Exception {

        when(bookingService.getBookingById(booking.getId())).thenReturn(Optional.of(booking));

        doNothing().when(bookingService).deleteBooking(booking.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/bookings/1")).andExpect(status().isNoContent());
    }

}
