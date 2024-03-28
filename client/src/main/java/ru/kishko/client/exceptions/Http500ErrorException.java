package ru.kishko.client.exceptions;

public class Http500ErrorException extends RuntimeException {
    public Http500ErrorException(String message) {
        super(message);
    }
}
