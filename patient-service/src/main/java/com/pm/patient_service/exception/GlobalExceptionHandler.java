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
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> err = new HashMap<>();
        err.put("success","false");
        ex.getBindingResult().getFieldErrors().forEach(
                e -> err.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String ,String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        log.warn("Email address already exists {}",ex.getMessage());
        Map<String,String> err=new HashMap<>();
        err.put("success","false");
        err.put("message","Email address already exists");

        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String,String>> handlePatientNotFoundException(PatientNotFoundException ex){
        log.warn("Patient not found with this id {}",ex.getMessage());
        Map<String,String> err=new HashMap<>();
        err.put("success","false");
        err.put("message","Patient not found");
        return ResponseEntity.badRequest().body(err);
    }

}
