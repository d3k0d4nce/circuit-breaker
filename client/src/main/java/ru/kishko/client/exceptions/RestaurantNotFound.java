package ru.kishko.client.exceptions;

public class RestaurantNotFound extends RuntimeException {
    public RestaurantNotFound(String message) {
        super(message);
    }
}