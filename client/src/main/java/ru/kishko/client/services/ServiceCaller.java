package ru.kishko.client.services;

import org.springframework.http.ResponseEntity;

public interface ServiceCaller {
    ResponseEntity<?> call() throws Exception;
}



