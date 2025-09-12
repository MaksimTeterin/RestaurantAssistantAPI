package com.example.restaurantassistantrestapi.model;

import jakarta.persistence.*;

import lombok.*;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String specialStatus;
    @Setter(AccessLevel.NONE)
    private UUID chatId = UUID.randomUUID();
}
