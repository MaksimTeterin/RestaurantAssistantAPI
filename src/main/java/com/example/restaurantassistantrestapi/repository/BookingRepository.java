package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {
}
