package com.example.restaurantassistantrestapi.service;

import com.example.restaurantassistantrestapi.model.Booking;
import com.example.restaurantassistantrestapi.model.BusinessDay;
import com.example.restaurantassistantrestapi.model.RestaurantTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Map;

@Service
public class AIToolService {

    private static final Logger logger = LoggerFactory.getLogger(AIToolService.class);

    private final BookingService bookingService;
    private final RestaurantTableService restaurantTableService;
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final BusinessDayService businessDayService;

    public AIToolService(BookingService bookingService, RestaurantTableService restaurantTableService,
                         RestaurantService restaurantService, UserService userService,
                         BusinessDayService businessDayService) {
        this.bookingService = bookingService;
        this.restaurantTableService = restaurantTableService;
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.businessDayService = businessDayService;
        logger.info("AIToolService initialized successfully.");
    }

    @Tool(description = "Get the current date and time in the user's timezone")
    public String getCurrentDateTime() {
        logger.info("Calling getCurrentDateTime");
        try {
            String result = LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
            logger.info("getCurrentDateTime result: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("Error in getCurrentDateTime: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Tool(description = "Get current day of the week")
    public String getCurrentDayOfWeek() {
        logger.info("Calling getCurrentDayOfWeek");
        try {
            String result = LocalDateTime.now().getDayOfWeek().toString();
            logger.info("getCurrentDayOfWeek result: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("Error in getCurrentDayOfWeek: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Tool(description = "Get user email from context")
    public String getUserEmail(ToolContext toolContext) {
        logger.info("Calling getUserEmail with context");
        try {
            String userEmail = toolContext.getContext().get("userEmail").toString();
            logger.info("getUserEmail successful: {}", userEmail);
            return userEmail;
        } catch (Exception e) {
            logger.error("Error retrieving userEmail from ToolContext: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Tool(description = "Get all bookings")
    public String getAllBookings() {
        logger.info("Calling getAllBookings");
        try {
            String result = bookingService.getAllBookings().toString();
            logger.info("getAllBookings retrieved {} records", result.length());
            return result;
        } catch (Exception e) {
            logger.error("Error in getAllBookings: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Tool(description = "Get all restaurants")
    public String getAllRestaurant() {
        logger.info("Calling getAllRestaurant");
        try {
            String result = restaurantService.getAllRestaurants().toString();
            logger.info("getAllRestaurant result size: {}", result.length());
            return result;
        } catch (Exception e) {
            logger.error("Error in getAllRestaurant: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Tool(description = "Get description of the restaurant")
    public String getRestaurantDescription(ToolContext toolContext) {
        logger.info("Calling getRestaurantDescription");
        try {
            Integer restaurantId = (Integer) toolContext.getContext().get("restaurantId");
            String info = restaurantService.getRestaurantDescriptionById(restaurantId);
            logger.info("getRestaurantDescription successful for ID: {}", restaurantId);
            return info;
        } catch (Exception e) {
            logger.error("Error in getRestaurantDescription: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Tool(description = "Get restaurants name")
    public String getRestaurantName(ToolContext toolContext) {
        logger.info("Calling getRestaurantName");
        try {
            Integer restaurantId = (Integer) toolContext.getContext().get("restaurantId");
            String restaurantName = restaurantService.getRestaurantById(restaurantId).orElseThrow().getName();
            logger.info("getRestaurantName successful for ID: {}", restaurantId);
            return restaurantName;
        } catch (Exception e) {
            logger.error("Error in getRestaurantName: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Tool(description = "Get user details by email")
    public String getUserByEmail(ToolContext toolContext) {
        logger.info("Calling getUserByEmail from context");
        try {
            String email = (String) toolContext.getContext().get("userEmail");
            String details = userService.getUserByEmail(email).toString();
            logger.info("getUserByEmail successful for: {}", email);
            return details;
        } catch (Exception e) {
            logger.error("Error in getUserByEmail: {}", e.getMessage(), e);
            throw e;
        }
    }

    private static final DateTimeFormatter FLEXIBLE_FORMATTER = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .optionalStart()
            .appendOffsetId()
            .optionalEnd()
            .toFormatter();

    @Tool(description = "Check if the restaurant is open on a specific date and time. Returns a message if closed, or 'OPEN' if it is working.")
    public String checkRestaurantStatus(String requestedDateTime, int restaurantId) {
        logger.info("Checking restaurant status for ID: {} at {}", restaurantId, requestedDateTime);
        try {
            LocalDateTime requestedTimeAsLocalDateTime = LocalDateTime.parse(requestedDateTime, FLEXIBLE_FORMATTER);
            String dayOfWeek = requestedTimeAsLocalDateTime.getDayOfWeek().toString();

            List<BusinessDay> businessDays = businessDayService.getBusinessDaysByRestaurantId(restaurantId);

            if(businessDays.stream().noneMatch(businessDay -> businessDay.getDayOfWeek() == requestedTimeAsLocalDateTime.getDayOfWeek())){
                return "The restaurant is not open on " + dayOfWeek + ". Please check our working hours and suggest another date.";
            }
            else return "The restaurant is opened on " + dayOfWeek;

        } catch (Exception e) {
            logger.error("Error checking restaurant status: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Tool(description = """
Create a booking using a specific tableId.
You MUST:
0. Make sure that restaurant is opened on the day of the demanded booking, if not, tell the user that restaurant is closed on a demanded day and suggest another date when restaurant is open. If restaurant works on a demanded day, proceed to next steps.
1. First call findAvailableTables to get valid tables
2. Choose the smallest table that fits the number of guests
3. If booking fails, try another table from the list
4. If no tables are available, suggest another time

Do NOT guess tableId.
""")
    public String createBooking(Booking booking) {
        logger.info("Attempting to create booking for user: {}", userService.getUserById(booking.getUserId()));
        try {
            String result = bookingService.addBooking(booking).toString();
            logger.info("createBooking successful. Result: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("Failed to create booking: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Tool(description = "Get all restaurant tables")
    public String getAllRestaurantTables() {
        logger.info("Calling getAllRestaurantTables");
        try {
            String result = restaurantTableService.getAllRestaurantTables().toString();
            logger.debug("getAllRestaurantTables result: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("Error in getAllRestaurantTables: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Tool(description = "Update booking with new data")
    public String updateBooking(Booking booking) {
        logger.info("Attempting to update booking ID: {}", booking.getId());
        if(booking.getBookingStart().isBefore(LocalDateTime.now())){
            return "You can not update a booking that has already started";
        }
        try {
            String result = bookingService.updateBooking(booking).toString();
            logger.info("updateBooking successful for ID: {}", booking.getId());
            return result;
        } catch (Exception e) {
            logger.error("Error updating booking {}: {}", booking.getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Tool(description = "Delete booking")
    public void deleteBooking(int id) {
        logger.info("Attempting to delete booking ID: {}", id);
        try {
            bookingService.deleteBooking(id);
            logger.info("Booking {} deleted successfully", id);
        } catch (Exception e) {
            logger.error("Error deleting booking {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Tool(description = "Find all bookings by user email")
    public String findBookingsByEmail(ToolContext toolContext) {
        logger.info("Calling findBookingsByEmail");
        try {
            String email = (String) toolContext.getContext().get("userEmail");
            String result = bookingService.getBookingsByUserEmail(email).toString();
            logger.info("findBookingsByEmail found records for: {}", email);
            return result;
        } catch (Exception e) {
            logger.error("Error finding bookings by email: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Tool(description = "Get business day details")
    public String getBusinessDayDetails(ToolContext toolContext) {
        logger.info("Calling getBusinessDayDetails");
        int restaurantId = (int) toolContext.getContext().get("restaurantId");
        try {
            String result = businessDayService.getBusinessDaysByRestaurantId(restaurantId).toString();
            logger.info("Successfully retrieved business day details. Result length: {}",
                    result != null ? result.length() : 0);
            return result;
        } catch (Exception e) {
            logger.error("Error in getBusinessDayDetails: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Tool(description = "Find available tables for given date, time and number of guests.")
    public String findAvailableTables(String startTime, int guestNumber, int restaurantId) {
        logger.info("Searching tables: Start={}, Guests={}, RestID={}", startTime, guestNumber, restaurantId);
        try {
            LocalDateTime startTimeAsLocalDateTime = LocalDateTime.parse(startTime);

            List<RestaurantTable> suitableTables = restaurantTableService.findAvailableTables(restaurantId, guestNumber);
            List<RestaurantTable> available = bookingService.filterAvailableTablesForParticularTime(suitableTables, startTimeAsLocalDateTime);

            logger.info("Found {} available tables", available.size());
            return available.toString();
        } catch (Exception e) {
            logger.error("Error in findAvailableTables: {}", e.getMessage(), e);
            throw e;
        }
    }
}