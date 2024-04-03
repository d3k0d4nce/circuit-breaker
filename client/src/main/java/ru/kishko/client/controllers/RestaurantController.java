package ru.kishko.client.controllers;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kishko.client.dtos.RestaurantDTO;
import ru.kishko.client.services.RestaurantService;
import ru.kishko.client.services.ServiceCaller;
import ru.kishko.client.utils.CircuitBreakerWithRetry;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final CircuitBreakerWithRetry circuitBreaker;

    @GetMapping("/reviews")
    public ResponseEntity<?> getRestaurantWithReviews(@RequestParam("description") String description,
                                                      @RequestParam("mark") Double mark) throws Exception {
        ServiceCaller serviceCaller = () -> new ResponseEntity<>(restaurantService.create(description, mark), HttpStatus.OK);
        ResponseEntity<?> response = circuitBreaker.executeRequestWithCircuitBreakerAndRetry(serviceCaller);
        return response;
    }

}
