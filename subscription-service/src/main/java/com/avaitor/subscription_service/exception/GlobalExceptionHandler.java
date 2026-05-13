package com.avaitor.subscription_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateSubscriptionRequestException.class)
    public ResponseEntity<Map<String,String>> duplicateSubscriptionReqExceptionHandler(DuplicateSubscriptionRequestException ex){
        HashMap<String, String> errors = new HashMap<>();
        errors.put("Error", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> illegalArguementExceptionHandler(IllegalArgumentException ex){
        HashMap<String, String> errors = new HashMap<>();
        errors.put("Error", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }
}
