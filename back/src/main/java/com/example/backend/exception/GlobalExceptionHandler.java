package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling exceptions throughout the application.
 * Specifically handles ProductNotFoundException by returning a custom response.
 * Author: Youssef Elouafi
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ProductNotFoundException and returns a custom error message and 404 NOT FOUND status.
     *
     * @param ex The exception to be handled.
     * @return A ResponseEntity containing the error message and the corresponding HTTP status code.
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    /**
     * Handles MethodArgumentNotValidException for validation errors.
     * Returns a map of field names and error messages with a 400 BAD REQUEST status.
     *
     * @param ex The exception to be handled.
     * @return A ResponseEntity containing a map of validation errors and the corresponding HTTP status code.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}