package ru.kishko.client.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.kishko.client.services.RestaurantService;

@Component
@RequiredArgsConstructor
public class CircuitBreakerWithRetry {
    private static final int FAILURE_THRESHOLD = 3;
    private static final int MAX_RETRIES = 3;
    private int failureCount = 0;
    private boolean isCircuitOpen = false;
    private final RestaurantService restaurantService;

    public ResponseEntity<?> executeRequestWithCircuitBreakerAndRetry(Long restaurantId) throws InterruptedException {
        int retryCount = 0;
        while (retryCount < MAX_RETRIES) {
            if (isCircuitOpen) {
                System.out.println("Circuit is open. Request not executed.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Circuit is open. Request not executed.");
            }

            try {

                ResponseEntity<String> responseEntity = new ResponseEntity<>(restaurantService.getRestaurantWithReviews(restaurantId), HttpStatus.OK);
                // Make the request to the service
                // If successful, reset failure count
                System.out.println("Request executed successfully.");
                failureCount = 0;
                return responseEntity;
            } catch (Exception e) {
                // If request fails, increment failure count
                failureCount++;
                if (failureCount >= FAILURE_THRESHOLD) {
                    isCircuitOpen = true;
                    System.out.println("Circuit opened due to repeated failures.");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Circuit opened due to repeated failures.");
                }
                System.out.println("Request failed. Retrying...");
                retryCount++;
                Thread.sleep(10000);
            }
        }

        System.out.println("Max retries exceeded. Request failed.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Max retries exceeded. Request failed.");
    }

    public void reset() {
        isCircuitOpen = false;
        failureCount = 0;
        System.out.println("Circuit closed. Resetting failure count.");
    }
}