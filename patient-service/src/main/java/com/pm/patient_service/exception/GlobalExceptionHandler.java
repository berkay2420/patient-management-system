package com.pm.patient_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException exception){

        Map<String, String > errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EMailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(
            EMailAlreadyExistsException exception){

        log.warn("Email Address Alreadt Exists {}", exception.getMessage());

        Map<String, String> erorrs = new HashMap<>();
        erorrs.put("message", "This e-mail address already exists");
        return ResponseEntity.badRequest().body(erorrs);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String >> handlePatientNotFoundException(
            PatientNotFoundException ex){

        log.warn("User Not Found {}", ex.getMessage());

        Map<String, String> erorrs = new HashMap<>();
        erorrs.put("message", "Patient Not Found In The System");
        return ResponseEntity.badRequest().body(erorrs);
    }

}
