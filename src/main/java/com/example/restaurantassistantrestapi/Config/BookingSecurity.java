package com.example.restaurantassistantrestapi.Config;

import com.example.restaurantassistantrestapi.model.User;
import com.example.restaurantassistantrestapi.service.BookingService;
import com.example.restaurantassistantrestapi.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingSecurity {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private RestaurantService restaurantService;

    public boolean isBookingOwner(int bookingId, User user) {
        return bookingService.getBookingById(bookingId)
                .map(b -> b.getUserId() == (user.getId()))
                .orElse(false);
    }

    public boolean isRestaurantOwner(int restaurantId, User user) {
        return restaurantService.getRestaurantById(restaurantId).orElseThrow().getOwnerId() == user.getId();
    }
}
