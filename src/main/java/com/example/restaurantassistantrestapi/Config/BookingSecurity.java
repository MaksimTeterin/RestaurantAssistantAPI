package com.example.restaurantassistantrestapi.Config;

import com.example.restaurantassistantrestapi.model.User;
import com.example.restaurantassistantrestapi.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingSecurity {
    @Autowired
    private BookingService bookingService;

    public boolean isOwner(long bookingId, User user) {
        return bookingService.getBookingById(bookingId)
                .map(b -> b.getUserId() == (user.getId()))
                .orElse(false);
    }
}
