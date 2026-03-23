package com.example.restaurantassistantrestapi.service;

import com.example.restaurantassistantrestapi.model.Booking;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class AIToolService {

    private final BookingService bookingService;
    private final RestaurantTableService restaurantTableService;
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final BusinessDayService businessDayService;

    public AIToolService(BookingService bookingService, RestaurantTableService restaurantTableService, RestaurantService restaurantService, UserService userService, BusinessDayService businessDayService) {
        this.bookingService = bookingService;
        this.restaurantTableService = restaurantTableService;
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.businessDayService = businessDayService;
    }

    @Tool(description = "Get the current date and time in the user's timezone")
    public String getCurrentDateTime() {
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }

    @Tool(description = "Get user email from context")
    public String getUserEmail(ToolContext toolContext) {
        return toolContext.getContext().get("userEmail").toString();
    }

    @Tool(description = "Get all bookings")
    public String getAllBookings() {
        return bookingService.getAllBookings().toString();
    }

    @Tool(description = "Get all restaurant")
    public String getAllRestaurant() {
        return restaurantService.getAllRestaurants().toString();
    }

    @Tool(description = "Get information about restaurant")
    public String getRestaurantInfo(ToolContext toolContext) {
        Integer restaurantIdFromContext = (Integer) toolContext.getContext().get("restaurantId");
        return restaurantService.getRestaurantDescriptionById(restaurantIdFromContext);
    }

    @Tool(description = "Get user details by email")
    public String getUserByEmail(ToolContext toolContext) {
        Map<String, Object> context = toolContext.getContext();
        String userEmail = (String) context.get("userEmail");
        System.out.println("User email: " + userEmail);

        return userService.getUserByEmail(userEmail).toString();
    }


    @Tool(description = "Create a booking, if there are available tables for the needed time, pick a table by your self from existing tables, if no tables available for needed time, then offer another time. Booking Id will be set automatically, for clientId use  If you could not create booking, explain the problem in a technical way")
    public String createBooking(Booking booking){
        return bookingService.addBooking(booking).toString();
    }

    @Tool(description = "Get all restaurant tables")
    public String getAllRestaurantTables() {
        return restaurantTableService.getAllRestaurantTables().toString();
    }

    //change Booking
    @Tool(description = "Update booking with new data, booking start should be at least 2 hours in advance before restaurant closing, table should be changed if guest wants to have more guests than current table can fit")
    public String updateBooking(Booking booking){
        return bookingService.updateBooking(booking).toString();
    }

    //delete Booking
    @Tool(description = "Delete booking")
    public void deleteBooking(int id){
        bookingService.deleteBooking(id);
    }

    @Tool(description = "Find all bookings by user email")
    public String findBookingsByEmail(ToolContext toolContext){
        Map<String, Object> context = toolContext.getContext();
        String userEmail = (String) context.get("userEmail");

        return bookingService.getBookingsByUserEmail(userEmail).toString();
    }


    // getBusinessDay details
    @Tool(description = "Get business day details")
    public String getBusinessDayDetails(){
        return businessDayService.getBusinessDays().toString();
    }

    @Tool(description = "Get all restaurant tables")
    public String getTables(){
        return restaurantTableService.getAllRestaurantTables().toString();
    }
}
