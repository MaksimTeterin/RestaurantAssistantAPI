package com.example.restaurantassistantrestapi.RepositoryTests;

import com.example.restaurantassistantrestapi.model.Booking;
import com.example.restaurantassistantrestapi.repository.BookingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookingRepositoryTests {
    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void BookingRepository_SaveAll_ReturnsSavedBooking() {
        //Arrange
        Booking booking = new Booking();

        //Act
        Booking savedBooking = bookingRepository.save(booking);

        //Assert
        Assertions.assertThat(savedBooking).isNotNull();
        Assertions.assertThat(savedBooking.getId()).isGreaterThan(0);
    }

    @Test
    public void BookingRepository_FindAll_ReturnsMoreThanOneBooking() {
        Booking booking = new Booking();
        Booking booking1 = new Booking();

        bookingRepository.save(booking);
        bookingRepository.save(booking1);

        List<Booking> bookings = (List<Booking>) bookingRepository.findAll();

        Assertions.assertThat(bookings.size()).isEqualTo(2);
        Assertions.assertThat(bookings).isNotNull();
    }

    @Test
    public void BookingRepository_FindById_ReturnsBooking() {
        Booking booking = new Booking();

        bookingRepository.save(booking);

        Booking bookingFoundById = bookingRepository.findById((long) booking.getId()).get();

        Assertions.assertThat(bookingFoundById).isNotNull();
    }

    @Test
    public void BookingRepository_DeleteById_ReturnsBooking() {
        Booking booking = new Booking();

        bookingRepository.save(booking);

        bookingRepository.deleteById((long) booking.getId());
        Optional<Booking> bookingReturned = bookingRepository.findById((long) booking.getId());

        Assertions.assertThat(bookingReturned).isEmpty();
    }

    @Test
    public void BookingRepository_save_ReturnsSavedBooking() {
        Booking booking = new Booking();

        bookingRepository.save(booking);

        Assertions.assertThat(booking).isNotNull();
        Assertions.assertThat(booking.getId()).isGreaterThanOrEqualTo(0);
    }
}
