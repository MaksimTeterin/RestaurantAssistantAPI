package com.example.restaurantassistantrestapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private Client client;
    private Date bookingStart;
    private int guestNumber;
    @OneToOne
    private RestaurantTable table;
}
