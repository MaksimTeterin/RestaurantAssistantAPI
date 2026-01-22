package com.example.restaurantassistantrestapi.controller;

import com.example.restaurantassistantrestapi.model.Booking;
import com.example.restaurantassistantrestapi.model.User;
import com.example.restaurantassistantrestapi.model.UserRoles;
import com.example.restaurantassistantrestapi.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PreAuthorize("hasAnyRole('USER', 'SYSTEM_ADMIN')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Booking> getBookingById(@PathVariable long id, @AuthenticationPrincipal User authenticatedUser){
        if(authenticatedUser.getId() != id && authenticatedUser.getUserRoles() != UserRoles.ROLE_SYSTEM_ADMIN){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed to access this resource");
        }

        return bookingService.getBookingById(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'SYSTEM_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@RequestBody Booking booking, @AuthenticationPrincipal User authenticatedUser){
        if(booking.getUser() != authenticatedUser && authenticatedUser.getUserRoles() != UserRoles.ROLE_SYSTEM_ADMIN){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed to do this");
        }
        return bookingService.addBooking(booking);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable long id, @AuthenticationPrincipal User authenticatedUser){
        if(bookingService.getBookingById(id).get().getUser().getId() != authenticatedUser.getId() && authenticatedUser.getUserRoles() != UserRoles.ROLE_SYSTEM_ADMIN){}
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
