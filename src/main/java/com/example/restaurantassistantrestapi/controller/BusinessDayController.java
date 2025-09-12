package com.example.restaurantassistantrestapi.controller;

import com.example.restaurantassistantrestapi.model.BusinessDay;
import com.example.restaurantassistantrestapi.service.BusinessDayService;
import com.example.restaurantassistantrestapi.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/businessdays")
public class BusinessDayController {

    @Autowired
    private BusinessDayService businessDayService;
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<BusinessDay> getBusinessDays() {
        return businessDayService.getBusinessDays();
    }

    @GetMapping("/{id}")
    public Optional<BusinessDay> getBusinessDayById(@PathVariable long id) {
        return businessDayService.getBusinessDayById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessDay createBusinessDay(@RequestBody BusinessDay businessDay, @RequestParam int id) {
        businessDayService.addBusinessDay(businessDay, restaurantService.getRestaurantById(id));
        return businessDay;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessDay(@PathVariable long id) {
        businessDayService.deleteBusinessDay(id);
        return ResponseEntity.noContent().build();
    }

}
