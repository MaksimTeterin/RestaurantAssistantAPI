package com.example.restaurantassistantrestapi.service;

import com.example.restaurantassistantrestapi.model.BusinessDay;
import com.example.restaurantassistantrestapi.model.Restaurant;
import com.example.restaurantassistantrestapi.repository.BusinessDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessDayService {

    private final BusinessDayRepository businessDayRepository;

    @Autowired
    public BusinessDayService(BusinessDayRepository businessDayRepository) {
        this.businessDayRepository = businessDayRepository;
    }

    public List<BusinessDay> getBusinessDays() {
        return (List<BusinessDay>) businessDayRepository.findAll();
    };

    public Optional<BusinessDay> getBusinessDayById(long businessDayId) {
        return businessDayRepository.findById(businessDayId);
    }

    public BusinessDay addBusinessDay(BusinessDay businessDay) {
        return businessDayRepository.save(businessDay);
    }

    public void deleteBusinessDay(long businessDayId) {
        BusinessDay businessDay = businessDayRepository.findById(businessDayId).get();
        businessDayRepository.delete(businessDay);
    }
}
