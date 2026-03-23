package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.Booking;
import com.example.restaurantassistantrestapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByUserId(long userId);
}
