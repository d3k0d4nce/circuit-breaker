package ru.kishko.server.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class Exception {

    private final String message;

    private final Throwable throwable;

    private final HttpStatus httpStatus;

}
