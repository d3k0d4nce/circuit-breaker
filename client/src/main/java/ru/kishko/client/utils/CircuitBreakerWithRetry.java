package ru.kishko.client.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.kishko.client.exceptions.RestaurantNotFound;
import ru.kishko.client.services.RestaurantService;
import ru.kishko.client.services.ServiceCaller;

@Component
@RequiredArgsConstructor
public class CircuitBreakerWithRetry {
    private static final int MAX_RETRIES = 3;
    private int retryCount = 0;
    private boolean isCircuitOpen = false;
    private final RestaurantService restaurantService;

    public ResponseEntity<?> executeRequestWithCircuitBreakerAndRetry(ServiceCaller serviceCaller) throws Exception {
        while (retryCount < MAX_RETRIES) {
            if (isCircuitOpen) {
                System.out.println("Circuit is open. Request not executed.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Circuit is open. Request not executed.");
            }

            try {
                ResponseEntity<?> result = serviceCaller.call();
                // If request is successful, reset failure count
                System.out.println("Request executed successfully.");
                reset();
                return result;
            } catch (Exception e) {
                if (e instanceof RestaurantNotFound) {
                    throw e;
                }
                else if (e instanceof RuntimeException) {
                    System.out.println("Request failed with 500 error. Retrying...");
                    retryCount++;
                    Thread.sleep(10000);
                } else {
                    throw e; // rethrow the exception if it's not a 500 error
                }
            }
        }

        reset();
        System.out.println("Max retries exceeded. Request failed.");
        return new ResponseEntity<>(restaurantService.getAllRestaurants(), HttpStatus.OK);
    }


    public void reset() {
        isCircuitOpen = false;
        retryCount = 0;
        System.out.println("Circuit closed. Resetting failure count.");
    }
}