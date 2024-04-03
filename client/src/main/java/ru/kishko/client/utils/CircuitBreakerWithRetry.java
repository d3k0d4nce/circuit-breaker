package ru.kishko.client.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.kishko.client.exceptions.CustomException;
import ru.kishko.client.services.ServiceCaller;

@Component
@RequiredArgsConstructor
public class CircuitBreakerWithRetry {
    private static final int MAX_RETRIES = 3;
    private int retryCount = 0;
    private boolean isCircuitOpen = false;

    public ResponseEntity<?> executeRequestWithCircuitBreakerAndRetry(ServiceCaller serviceCaller) throws Exception {

        if (isCircuitOpen) {
            System.out.println("Circuit is open. Request not executed.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Circuit is open. Request not executed.");
        }

        while (retryCount < MAX_RETRIES) {
            try {
                isCircuitOpen = true;
                ResponseEntity<?> result = serviceCaller.call();
                // If request is successful, reset failure count
                System.out.println("Request executed successfully.");
                reset();
                return result;
            } catch (WebClientResponseException e) {
                if (!e.getStatusCode().is5xxServerError()) {
                    reset();
                    throw new CustomException(e.getMessage());
                }
                System.out.println("Request failed with 500 error. Retrying...");
                retryCount++;
                Thread.sleep(10000);
            }
        }

        reset();
        System.out.println("Max retries exceeded. Request failed.");
        return new ResponseEntity<>("Max retries exceeded. Request failed.", HttpStatus.OK);
    }


    public void reset() {
        isCircuitOpen = false;
        retryCount = 0;
        System.out.println("Circuit closed. Resetting failure count.");
    }
}
