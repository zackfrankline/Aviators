package com.aviator.content_servive.exception;

import org.apache.coyote.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String,String>> handleSlugAlreadyExistsException(DuplicateResourceException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("Error:", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("Error:", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("Error:" , ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(IllegalArgumentException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("Error:" , ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("Error:" , ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }
}
