package com.example.restaurantassistantrestapi.ServiceTests;

import com.example.restaurantassistantrestapi.model.Booking;
import com.example.restaurantassistantrestapi.repository.BookingRepository;
import com.example.restaurantassistantrestapi.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTests {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    public void getAllBookings_shouldReturnAllBookings() {
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        List<Booking> list = Arrays.asList(booking2, booking1);

        when(bookingRepository.findAll()).thenReturn(list);

        List<Booking> result = bookingService.getAllBookings();

        assertNotNull(result);
        assertEquals(list, result);
    }

    @Test
    public void getBookingById_shouldReturnBooking() {
        Booking booking = new Booking();
        booking.setId(1L);

        when(bookingRepository.findById(booking.getId()))
                .thenReturn(Optional.of(booking));

        Optional<Booking> result = bookingService.getBookingById(booking.getId());

        assertTrue(result.isPresent());
        assertEquals(booking, result.get());
    }


    @Test
    public void addBooking_shouldAddBooking_and_ReturnCreatedBooking(){
        Booking booking = new Booking();

        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking result = bookingService.addBooking(booking);

        assertNotNull(result);
        assertEquals(booking, result);
    }

    @Test
    public void deleteBooking_shouldDeleteBooking_and_ReturnNothing(){
        Booking booking = new Booking();
        booking.setId(1L);

        doNothing().when(bookingRepository).deleteById(booking.getId());

        bookingService.deleteBooking(booking.getId());

        verify(bookingRepository, times(1)).deleteById(booking.getId());
    }
}
