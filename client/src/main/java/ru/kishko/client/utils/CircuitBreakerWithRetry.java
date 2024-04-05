package ru.kishko.client.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.kishko.client.exceptions.CustomException;
import ru.kishko.client.services.ServiceCaller;

import java.util.Timer;
import java.util.TimerTask;

@Component
@RequiredArgsConstructor
public class CircuitBreakerWithRetry {

    private static boolean isCircuitOpen = false;

    public ResponseEntity<?> executeRequestWithCircuitBreakerAndRetry(ServiceCaller serviceCaller) throws Exception {

        if (isCircuitOpen) {
            System.out.println("Circuit is open. Request not executed.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Circuit is open. Request not executed.");
        }

        try {
            ResponseEntity<?> result = serviceCaller.call();
            System.out.println("Request executed successfully.");
            return result;
        } catch (WebClientResponseException e) {
            if (!e.getStatusCode().is5xxServerError()) {
                throw new CustomException(e.getMessage());
            }
            System.out.println("Request failed with 500 error. Starting waiting time...");
            startTime(10000L);
        }

        System.out.println("INTERNAL_SERVER_ERROR. Wait 10 seconds");
        return new ResponseEntity<>("INTERNAL_SERVER_ERROR. Waiting 10 seconds.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public void startTime(Long time) {
        isCircuitOpen = true;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                isCircuitOpen = false;
            }
        }, time);
    }
}
