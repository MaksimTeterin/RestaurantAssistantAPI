package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.Booking;
import com.example.restaurantassistantrestapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByUserId(long userId);


    @Query("""
    SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
    FROM Booking b
    WHERE b.restaurantTableId = :tableId
      AND b.bookingStart < :newEnd
      AND b.bookingStart > :newStartMinus3h
""")
    boolean existsConflictingBooking(
            @Param("tableId") int tableId,
            @Param("newStartMinus3h") LocalDateTime newStartMinus3h,
            @Param("newEnd") LocalDateTime newEnd
    );

    List<Booking> findAllByBookingStartBetween(LocalDateTime bookingTimeStart, LocalDateTime bookingTimeEnd);

    List<Booking> getBookingsByRestaurantTableId(int restaurantTableId);


    List<Booking> getBookingsByRestaurantId(int restaurantId);
}
