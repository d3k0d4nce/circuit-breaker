package ru.kishko.client.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kishko.client.enums.Services;
import ru.kishko.client.services.RestaurantService;
import ru.kishko.client.services.ServiceCaller;
import ru.kishko.client.services.ServiceCallerWithId;
import ru.kishko.client.utils.CircuitBreakerWithRetry;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final CircuitBreakerWithRetry circuitBreaker;

    @GetMapping("/reviews")
    public ResponseEntity<?> getRestaurantWithReviews(@RequestParam("description") String description,
                                                      @RequestParam("mark") Double mark) throws Exception {
        return circuitBreaker.executeRequestWithCircuitBreakerAndRetry(
            createServiceCallerWithId(
                () -> new ResponseEntity<>(restaurantService.create(description, mark), HttpStatus.OK),
                Services.RESTAURANT_SERVICE
            )
        );
    }

    private ServiceCallerWithId createServiceCallerWithId(ServiceCaller serviceCaller, Services serviceName) {
        return new ServiceCallerWithId() {
            @Override
            public ResponseEntity<?> call() throws Exception {
                return serviceCaller.call();
            }

            @Override
            public Services getServiceId() {
                return serviceName;
            }
        };
    }
}
