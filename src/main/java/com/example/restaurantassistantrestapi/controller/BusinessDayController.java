package com.example.restaurantassistantrestapi.controller;

import com.example.restaurantassistantrestapi.model.BusinessDay;
import com.example.restaurantassistantrestapi.service.BusinessDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/businessdays")
public class BusinessDayController {

    @Autowired
    private BusinessDayService businessDayService;

    @GetMapping
    public List<BusinessDay> getBusinessDays() {
        return businessDayService.getBusinessDays();
    }

    @GetMapping("/{id}")
    public Optional<BusinessDay> getBusinessDayById(@PathVariable int id) {
        return businessDayService.getBusinessDayById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessDay createBusinessDay(@RequestBody BusinessDay businessDay) {
        businessDayService.addBusinessDay(businessDay);
        return businessDay;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBusinessDay(@PathVariable int id) {
        businessDayService.deleteBusinessDay(id);
    }

}
