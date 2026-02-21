package com.example.restaurantassistantrestapi.controller;

import com.example.restaurantassistantrestapi.model.Booking;
import com.example.restaurantassistantrestapi.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

    @PreAuthorize(
            "hasRole('SYSTEM_ADMIN') or @bookingSecurity.isOwner(#id, authentication.principal)"
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Booking> getBookingById(@PathVariable long id){
        return bookingService.getBookingById(id);
    }

    @PreAuthorize(
            "#booking.userId == authentication.principal.id or hasRole('SYSTEM_ADMIN')"
    )
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.addBooking(booking));
    }

    @PreAuthorize(
            "hasRole('SYSTEM_ADMIN') or @bookingSecurity.isOwner(#id, authentication.principal)"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable long id){
        bookingService.getBookingById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        bookingService.deleteBooking(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
