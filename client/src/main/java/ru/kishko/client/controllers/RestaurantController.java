package ru.kishko.client.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kishko.client.dtos.RestaurantDTO;
import ru.kishko.client.services.RestaurantService;
import ru.kishko.client.utils.CircuitBreakerWithRetry;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final CircuitBreakerWithRetry circuitBreaker;

    @PostMapping
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantDTO restaurant) {
        return new ResponseEntity<>(restaurantService.createRestaurant(restaurant), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        return new ResponseEntity<>(restaurantService.getAllRestaurants(), HttpStatus.OK);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable("restaurantId") Long restaurantId) {
        return new ResponseEntity<>(restaurantService.getRestaurantById(restaurantId), HttpStatus.OK);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDTO> updateRestaurantById(@PathVariable("restaurantId") Long restaurantId, @RequestBody RestaurantDTO restaurant) {
        return new ResponseEntity<>(restaurantService.updateRestaurantById(restaurantId, restaurant), HttpStatus.OK);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<String> deleteRestaurantById(@PathVariable("restaurantId") Long restaurantId) {
        return new ResponseEntity<>(restaurantService.deleteRestaurantById(restaurantId), HttpStatus.OK);
    }

    @GetMapping("/top/count")
    public ResponseEntity<List<RestaurantDTO>> findTop5RestaurantsByReviewsCount() {
        return new ResponseEntity<>(restaurantService.findTop5RestaurantsByReviewsCount(), HttpStatus.OK);
    }

    @GetMapping("/reviews/{restaurantId}")
    public ResponseEntity<?> getRestaurantWithReviews(@PathVariable("restaurantId") Long restaurantId) throws InterruptedException {
        ResponseEntity<?> response = circuitBreaker.executeRequestWithCircuitBreakerAndRetry(restaurantId);
        circuitBreaker.reset();
        return response;
    }

}
