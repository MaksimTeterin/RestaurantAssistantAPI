package com.example.restaurantassistantrestapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @OneToMany(cascade = CascadeType.ALL)
    private List<RestaurantTable> tables;


    @ElementCollection
    private List<Date> nonOperatingDays;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BusinessDay> businessDays;

    @Basic(optional = true)
    private String phone;

    private String generalDescription;


    public void addTables(RestaurantTable restaurantTable) {
        this.tables.add(restaurantTable);
    }

    public void addBusinessDays(BusinessDay businessDay) {
        this.businessDays.add(businessDay);
    }
}
