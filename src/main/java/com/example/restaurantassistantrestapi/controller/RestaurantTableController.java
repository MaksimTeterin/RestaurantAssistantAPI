package com.example.restaurantassistantrestapi.controller;

import com.example.restaurantassistantrestapi.model.RestaurantTable;
import com.example.restaurantassistantrestapi.service.RestaurantService;
import com.example.restaurantassistantrestapi.service.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restauranttables")
public class RestaurantTableController {

    @Autowired
    private RestaurantTableService restaurantTableService;
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantTable> getAllTables() {
        return restaurantTableService.getAllRestaurantTables();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTable> getTableById(@PathVariable int id) {
        Optional<RestaurantTable> restauranttable = restaurantTableService.getRestaurantTableById(id);
        return restauranttable.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantTable createTable(@RequestBody RestaurantTable restaurantTable) {
        return restaurantTableService.addRestaurantTable(restaurantTable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable int id) {
        restaurantTableService.deleteRestaurantTable(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<RestaurantTable>> getRestaurantTablesByRestaurantId(@PathVariable int id){
        System.out.println(restaurantService.getRestaurantById(id));
        return new ResponseEntity<>(restaurantTableService.getRestaurantTablesByRestaurantId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantTable> updateTable(@PathVariable int id, @RequestBody RestaurantTable restaurantTable){
        return new ResponseEntity<>(restaurantTableService.updateRestaurantTable(id, restaurantTable), HttpStatus.OK);
    }

}
