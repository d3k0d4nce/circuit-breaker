package ru.kishko.client.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClientExceptionHandler {

    @ExceptionHandler(value = {Http500ErrorException.class})
    public ResponseEntity<Object> handleHttp500ErrorException(Http500ErrorException userNotFoundException) {

        ClientException taskException = new ClientException(
                userNotFoundException.getMessage(),
                userNotFoundException.getCause(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return new ResponseEntity<>(taskException, taskException.getHttpStatus());

    }

    @ExceptionHandler(value = {RestaurantNotFound.class})
    public ResponseEntity<Object> handleRestaurantNotFoundException(RestaurantNotFound userNotFoundException) {

        ClientException taskException = new ClientException(
                userNotFoundException.getMessage(),
                userNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(taskException, taskException.getHttpStatus());

    }

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Object> handleCustomException(CustomException userNotFoundException) {

        ClientException taskException = new ClientException(
                userNotFoundException.getMessage(),
                userNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(taskException, taskException.getHttpStatus());

    }

}
