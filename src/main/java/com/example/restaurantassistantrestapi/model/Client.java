package com.example.restaurantassistantrestapi.model;

import jakarta.persistence.*;

import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String specialStatus;
}
