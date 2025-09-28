package com.example.restaurantassistantrestapi.ServiceTests;

import com.example.restaurantassistantrestapi.model.BusinessDay;
import com.example.restaurantassistantrestapi.repository.BusinessDayRepository;
import com.example.restaurantassistantrestapi.service.BusinessDayService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusinessDayServiceTests {

    @Mock
    private BusinessDayRepository businessDayRepository;

    @InjectMocks
    private BusinessDayService businessDayService;

    @Test
    public void getAllBusinessDays_shouldReturnAllBusinessDays() {
        BusinessDay businessDay1 = new BusinessDay();
        BusinessDay businessDay2 = new BusinessDay();
        List<BusinessDay> list = Arrays.asList(businessDay2, businessDay1);

        when(businessDayRepository.findAll()).thenReturn(list);

        List<BusinessDay> result = businessDayService.getBusinessDays();

        assertNotNull(result);
        assertEquals(list, result);
    }

    @Test
    public void getBusinessDayById_shouldReturnBusinessDay() {
        BusinessDay businessDay = new BusinessDay();
        businessDay.setId(1);

        when(businessDayRepository.findById((long) businessDay.getId()))
                .thenReturn(Optional.of(businessDay));

        Optional<BusinessDay> result = businessDayService.getBusinessDayById(businessDay.getId());

        assertTrue(result.isPresent());
        assertEquals(businessDay, result.get());
    }


    @Test
    public void addBusinessDay_shouldAddBusinessDay_and_ReturnCreatedBusinessDay(){
        BusinessDay businessDay = new BusinessDay();

        when(businessDayRepository.save(businessDay)).thenReturn(businessDay);

        BusinessDay result = businessDayService.addBusinessDay(businessDay);

        assertNotNull(result);
        assertEquals(businessDay, result);
    }

    @Test
    public void deleteBusinessDay_shouldDeleteBusinessDay_and_ReturnNothing(){
        BusinessDay businessDay = new BusinessDay();
        businessDay.setId(1);

        doNothing().when(businessDayRepository).deleteById((long) businessDay.getId());

        businessDayService.deleteBusinessDay(businessDay.getId());

        verify(businessDayRepository, times(1)).deleteById((long) businessDay.getId());
    }
}
