package com.example.restaurantassistantrestapi.controller;

import com.example.restaurantassistantrestapi.model.Booking;
import com.example.restaurantassistantrestapi.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Booking> getBookingById(@PathVariable long id){
        return bookingService.getBookingById(id);
    }

    @PreAuthorize("hasRole('RESTAURANT_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@RequestBody Booking booking){
        return bookingService.addBooking(booking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable long id){
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
