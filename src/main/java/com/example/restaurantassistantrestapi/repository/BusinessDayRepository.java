package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.BusinessDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessDayRepository extends JpaRepository<BusinessDay, Integer> {
    List<BusinessDay> findAllByRestaurantId(int id);
}
