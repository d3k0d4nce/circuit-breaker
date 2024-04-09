package ru.kishko.client.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.kishko.client.exceptions.CustomException;
import ru.kishko.client.services.ServiceCallerWithId;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class CircuitBreakerWithRetry {

    @Value("${circuitbreaker.pausetime}")
    private Long pauseTime;
    private static final Map<String, Boolean> serviceCircuitState = new HashMap<>();

    public ResponseEntity<?> executeRequestWithCircuitBreakerAndRetry(ServiceCallerWithId serviceCaller) throws Exception {

        String serviceId = String.valueOf(serviceCaller.getServiceId());

        if (serviceCircuitState.getOrDefault(serviceId, false)) {
            System.out.println("Circuit is open for service: " + serviceId + ". Request not executed.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Circuit is open for service: " + serviceId + ". Request not executed.");
        }

        try {
            ResponseEntity<?> result = serviceCaller.call();
            System.out.println("Request executed successfully for service: " + serviceId);
            return result;
        } catch (WebClientResponseException e) {
            if (!e.getStatusCode().is5xxServerError()) {
                throw new CustomException(e.getMessage());
            }
            System.out.println("Request failed with 500 error for service: " + serviceId + ". Starting waiting time...");
            startTime(serviceId);
        }

        System.out.println("INTERNAL_SERVER_ERROR for service: " + serviceId + ". Wait 10 seconds");
        return new ResponseEntity<>("INTERNAL_SERVER_ERROR for service: " + serviceId + ". Waiting 10 seconds.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public void startTime(String serviceId) {
        serviceCircuitState.put(serviceId, true);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            serviceCircuitState.put(serviceId, false);
            executor.shutdown();
        }, pauseTime, TimeUnit.MILLISECONDS);
    }
}
