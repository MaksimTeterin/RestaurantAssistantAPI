package com.example.restaurantassistantrestapi.ControllerTests;

import com.example.restaurantassistantrestapi.controller.BookingController;
import com.example.restaurantassistantrestapi.model.Booking;
import com.example.restaurantassistantrestapi.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = BookingController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService service;

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private BookingService bookingService;


    @Test
    public void BookingContorller_CreateBooking_ReturnCreated() throws Exception {
        Booking booking = Booking.builder().id(1).build();

        given(service.addBooking(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/bookings")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(booking)));

        response.andExpect((MockMvcResultMatchers.status().isCreated()));
    }

    @Test
    public void BookingController_GetBookingById_Return() throws Exception {
        int bookingId = 1;
        Booking booking = Booking.builder().id(bookingId).build();

        when(bookingService.getBookingById(bookingId)).thenReturn(Optional.of(booking));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/bookings/" + bookingId));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void BookingController_GetAllBookings_Return() throws Exception {
        Booking booking = Booking.builder().build();

        when(bookingService.getAllBookings()).thenReturn(List.of(booking));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/bookings"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void BookingController_DeleteBooking_ReturnString() throws Exception {
        int bookingId = 1;

        doNothing().when(service).deleteBooking(bookingId);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/bookings/" + bookingId));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
