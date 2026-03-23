package com.example.restaurantassistantrestapi.service;

import com.example.restaurantassistantrestapi.exception.ResourceNotFoundException;
import com.example.restaurantassistantrestapi.model.BusinessDay;
import com.example.restaurantassistantrestapi.repository.BusinessDayRepository;
import com.example.restaurantassistantrestapi.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessDayService {

    private final BusinessDayRepository businessDayRepository;
    private final RestaurantService restaurantService;

    @Autowired
    public BusinessDayService(BusinessDayRepository businessDayRepository, RestaurantService restaurantService) {
        this.businessDayRepository = businessDayRepository;
        this.restaurantService = restaurantService;
    }

    public List<BusinessDay> getBusinessDays() {
        return businessDayRepository.findAll();
    }

    public Optional<BusinessDay> getBusinessDayById(int businessDayId) {
        return businessDayRepository.findById(businessDayId);
    }

    public BusinessDay addBusinessDay(BusinessDay businessDay) {
        return businessDayRepository.save(businessDay);
    }

    public void deleteBusinessDay(int businessDayId) {
        businessDayRepository.deleteById(businessDayId);
    }

    public List<BusinessDay> getBusinessDaysByRestaurantId(int id){
        if(restaurantService.getRestaurantById(id).isPresent()){
            return businessDayRepository.findAllByRestaurantId(id);
        }
        throw new ResourceNotFoundException("Restaurant not found");
    }

    public BusinessDay updateBusinessDay(int id, BusinessDay businessDay) {
        BusinessDay existingBusinessDay = businessDayRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Business day not found with ID: " + id));
        existingBusinessDay.setOpenTime(businessDay.getOpenTime());
        existingBusinessDay.setCloseTime(businessDay.getCloseTime());
        return businessDayRepository.save(existingBusinessDay);
    }
}
