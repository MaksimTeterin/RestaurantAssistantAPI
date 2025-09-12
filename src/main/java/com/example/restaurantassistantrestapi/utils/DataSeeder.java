//package com.example.restaurantassistantrestapi.utils;
//
//import com.example.restaurantassistantrestapi.model.Booking;
//import com.example.restaurantassistantrestapi.model.Client;
//import com.example.restaurantassistantrestapi.model.Restaurant;
//import com.example.restaurantassistantrestapi.model.RestaurantTable;
//import com.example.restaurantassistantrestapi.service.BookingService;
//import com.example.restaurantassistantrestapi.service.ClientService;
//import com.example.restaurantassistantrestapi.service.RestaurantService;
//import com.example.restaurantassistantrestapi.service.RestaurantTableService;
//import com.github.javafaker.Faker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Component
//public class DataSeeder  implements ApplicationRunner {
//
//    Faker faker = new Faker();
//
//    @Autowired
//    private RestaurantService restaurantService;
//    private ClientService clientService;
//    private RestaurantTableService restaurantTableService;
//    private BookingService bookingService;
//
//    public DataSeeder(ClientService clientService, RestaurantTableService restaurantTableService, BookingService bookingService) {
//        this.clientService = clientService;
//        this.restaurantTableService = restaurantTableService;
//        this.bookingService = bookingService;
//        this.restaurantService = restaurantService;
//    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        if(restaurantService.getAllRestaurants().isEmpty()){
//            System.out.println("No restaurants found, creating a new one");
//
//            List<RestaurantTable> restaurantTables = new ArrayList<>();
//
//            Restaurant restaurant = new Restaurant();
//
//            for(int i = 0; i < 10; i++ ){
//                RestaurantTable restaurantTable = new RestaurantTable();
//                restaurantTable.setCapacity(faker.number().numberBetween(1, 10));
//                restaurantTables.add(restaurantTable);
//
//                restaurantTableService.addRestaurantTable(restaurantTable, restaurant);
//            }
//
//            restaurant.setName(faker.funnyName().name());
//            restaurant.setAddress(faker.address().streetAddress());
//            restaurant.setTables(restaurantTables);
//
//            restaurantService.addRestaurant(restaurant);
//
//            Client client = new Client();
//
//            client.setFirstName(faker.name().firstName());
//            client.setLastName(faker.name().lastName());
//            client.setBirthDate(faker.date().birthday());
//            client.setSpecialStatus("Default");
//
//            clientService.addClient(client);
//
//            Booking booking = new Booking();
//
//            booking.setClient(client);
//            booking.setGuestNumber(1);
//            booking.setTable(restaurantTables.get(1));
//            booking.setBookingStart(new Date());
//
//            bookingService.addBooking(booking);
//        }
//    }
//}
