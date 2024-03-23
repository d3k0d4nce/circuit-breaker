package ru.kishko.client.services;

import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface ServiceCaller {
    ResponseEntity<?> call() throws Exception;
}
