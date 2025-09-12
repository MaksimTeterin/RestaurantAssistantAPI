package com.example.restaurantassistantrestapi.repository;

import com.example.restaurantassistantrestapi.model.BusinessDay;
import org.springframework.data.repository.CrudRepository;

public interface BusinessDayRepository extends CrudRepository<BusinessDay, Long> {
}
