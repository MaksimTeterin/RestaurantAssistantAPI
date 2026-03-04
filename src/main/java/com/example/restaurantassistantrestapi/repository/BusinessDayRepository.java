package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.BusinessDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessDayRepository extends JpaRepository<BusinessDay, Integer> {
}
