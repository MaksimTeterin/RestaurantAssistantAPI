package com.example.restaurantassistantrestapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Time;
import java.time.DayOfWeek;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private DayOfWeek dayOfWeek;
    private Time openTime;
    private Time closeTime;
    private int restaurantId;
}
