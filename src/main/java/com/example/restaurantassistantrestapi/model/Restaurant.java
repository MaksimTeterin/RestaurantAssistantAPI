package com.example.restaurantassistantrestapi.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String address;
    @Basic(optional = true)
    private String phone;
    private String generalDescription;
    private int ownerId;
}
