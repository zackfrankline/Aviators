package com.aviator.jwt_security.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationException(MethodArgumentNotValidException ex){
        Instant now = Instant.now();
        String curDate = Date.from(now).toString();
        Map<String,Object> errors = new HashMap<>();
        Map<String,String> fieldErrors = new HashMap<>();
        errors.put("error", "Validation Error");
        errors.put("message", "Validation Error");
        errors.put("timestamp", curDate);
        ex.getBindingResult().getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
        errors.put("fieldErrors", fieldErrors);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String,String>> handleBadCredentialsException(BadCredentialsException ex){
        Instant now = Instant.now();
        String curDate = Date.from(now).toString();
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Bad Credentials");
        errors.put("message", "Invalid username or password. please try again.");
        errors.put("timestamp", curDate);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String,String>> handleAuthenticationException(AuthenticationException ex){
        Instant now = Instant.now();
        String curDate = Date.from(now).toString();
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Authentication Exception");
        errors.put("message", "Invalid username or password. please try again.");
        errors.put("timestamp", curDate);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleUsernameNotFoundException(UsernameNotFoundException ex){
        Instant now = Instant.now();
        String curDate = Date.from(now).toString();
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Username Invalid");
        errors.put("message", "Username not found. please try again.");
        errors.put("timestamp", curDate);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> handleIllegalArgumentException(IllegalArgumentException ex){
        Instant now = Instant.now();
        String curDate = Date.from(now).toString();
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Error");
        errors.put("message", ex.getMessage());
        errors.put("timestamp", curDate);
        return ResponseEntity.badRequest().body(errors);
    }
}
