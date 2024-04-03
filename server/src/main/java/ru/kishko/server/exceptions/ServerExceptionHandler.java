//package ru.kishko.server.exceptions;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class ServerExceptionHandler {
//
//    @ExceptionHandler(value = {RuntimeException.class})
//    public ResponseEntity<Object> handleProjectMethodArgumentNotValidException(RuntimeException methodArgumentNotValidException) {
//
//        Exception calculateException = new Exception(
//                methodArgumentNotValidException.getMessage(),
//                methodArgumentNotValidException.getCause(),
//                HttpStatus.BAD_REQUEST
//        );
//
//        return new ResponseEntity<>(calculateException, calculateException.getHttpStatus());
//
//    }
//
//}
