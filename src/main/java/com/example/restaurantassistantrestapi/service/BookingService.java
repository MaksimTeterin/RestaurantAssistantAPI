package com.example.restaurantassistantrestapi.service;

import com.example.restaurantassistantrestapi.model.Booking;
import com.example.restaurantassistantrestapi.model.RestaurantTable;
import com.example.restaurantassistantrestapi.repository.BookingRepository;
import com.example.restaurantassistantrestapi.repository.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final RestaurantTableService restaurantTableService;
    private final RestaurantService restaurantService;
    private final BusinessDayService businessDayService;
    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, UserService userService, RestaurantTableService restaurantTableService, RestaurantService restaurantService, BusinessDayService businessDayService, RestaurantTableRepository restaurantTableRepository) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.restaurantTableService = restaurantTableService;
        this.restaurantService = restaurantService;
        this.businessDayService = businessDayService;
        this.restaurantTableRepository = restaurantTableRepository;
    }

    @PreAuthorize("hasAnyRole('USER', 'SYSTEM_ADMIN')")
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('USER', 'SYSTEM_ADMIN')")
    public Optional<Booking> getBookingById(int id) {
        return bookingRepository.findById(id);
    }

    public Booking addBooking(Booking booking) {
        if(booking.getGuestNumber() > restaurantTableService.getRestaurantTableById(booking.getRestaurantTableId()).orElseThrow().getCapacity()){
            throw new RuntimeException("Table can not fill more than " + restaurantTableService.getRestaurantTableById(booking.getRestaurantTableId()).orElseThrow().getCapacity() + " guests");
        }

        LocalDateTime closingDateTime = businessDayService.getBusinessDayById(booking.getBusinessDayId()).orElseThrow().getCloseTime().toLocalTime()
                .atDate(booking.getBookingStart().toLocalDate());

        Duration diff = Duration.between(
                booking.getBookingStart(),
                closingDateTime
        );

        if (diff.compareTo(Duration.ofHours(2)) < 0) {
            throw new RuntimeException("Booking must be at least 2 hours before closing time");
        }

        if(bookingRepository.existsConflictingBooking(booking.getRestaurantTableId(), booking.getBookingStart().minusHours(3), booking.getBookingStart().plusHours(3))){
            throw new RuntimeException("There is already a booking for this table at this time");
        }

        if(booking.getBookingStart().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Booking must be in the future");
        }

        if(!(booking.getBookingStart().getDayOfWeek() == businessDayService.getBusinessDayById(booking.getBusinessDayId()).orElseThrow().getDayOfWeek())){
            throw new RuntimeException("Booking must be on the same day of the week as the business day");
        }

        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Booking booking) {
        Booking createdBooking = bookingRepository.findById(booking.getId()).orElseThrow();
        createdBooking.setBookingStart(booking.getBookingStart());
        createdBooking.setGuestNumber(booking.getGuestNumber());
        createdBooking.setRestaurantTableId(booking.getRestaurantTableId());

        return bookingRepository.save(createdBooking);
    }

    public void deleteBooking(int id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> getBookingsByUserEmail(String email){
        if(userService.userExistsByEmail(email))
        {
            return bookingRepository.findAllByUserId((userService.getUserByEmail(email).getId()));
        }
        throw new RuntimeException("User with email: " + email + " not found");
    }

    public List<RestaurantTable> filterAvailableTablesForParticularTime(List<RestaurantTable> restaurantTables, LocalDateTime startTime){
        List<Booking> bookingsWithinTimeOfSearch = bookingRepository.findAllByBookingStartBetween(startTime.minusHours(3), startTime.plusHours(3));

//        restaurantTables.forEach(restaurantTable -> {
//            if(bookingsWithinTimeOfSearch.stream().anyMatch(booking -> booking.getRestaurantTableId() == restaurantTable.getId())){
//                restaurantTables.remove(restaurantTable);
//            }
//        });

        bookingsWithinTimeOfSearch.forEach(booking -> restaurantTables.remove(restaurantTableService.getRestaurantTableById(booking.getRestaurantTableId()).orElseThrow()));
        return restaurantTables;
    }

    public List<Booking> getBookingsByRestaurantId(int restaurantId){
        if(restaurantService.getRestaurantById(restaurantId).isPresent()){
            return bookingRepository.getBookingsByRestaurantId(restaurantId);
        }
        throw new RuntimeException("Restaurant with id: " + restaurantId + " not found");
    }
}
