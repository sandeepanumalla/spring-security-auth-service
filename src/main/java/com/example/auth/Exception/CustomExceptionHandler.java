package com.example.auth.Exception;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleBeanValidation(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errorMap = new HashMap<>();
                methodArgumentNotValidException
                .getBindingResult()
                .getFieldErrors().forEach(e -> {
                    errorMap.put(e.getField(), e.getDefaultMessage());
                });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleUniqueConstraintViolation(DataIntegrityViolationException dataIntegrityViolationException) {
        String message = "An error occured, "+dataIntegrityViolationException.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(message);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException badCredentialsException) {
        String message = badCredentialsException.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
