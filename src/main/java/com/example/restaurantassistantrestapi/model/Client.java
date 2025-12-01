package com.example.restaurantassistantrestapi.model;

import jakarta.persistence.*;

import lombok.*;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
    private String email;
    @Setter(AccessLevel.NONE)
    private UUID chatId = UUID.randomUUID();

    public Client(String email, String fullName) { // Constructor to create new Clien entity, when user wants to getToken for the first time
        String[] splittedName = fullName.split(" ");
        this.firstName = splittedName[0];
        if(splittedName.length > 1 && Arrays.stream(splittedName).anyMatch(e -> splittedName[splittedName.length - 1].startsWith("("))) {
            this.lastName = splittedName[1];
        } else {
            lastName = splittedName[splittedName.length - 1];
        }
        this.email = email;
    }
}
