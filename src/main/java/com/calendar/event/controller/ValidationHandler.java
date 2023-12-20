package com.calendar.event.controller;

import com.calendar.event.exception.CreateEventRequestRejectedexception;
import com.calendar.event.exception.EventDoesNotExistsException;
import com.calendar.event.model.Error;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreateEventRequestRejectedexception.class)
    public ResponseEntity<Error> handleOutboundProcessingRejectedException(CreateEventRequestRejectedexception ex) {
        return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventDoesNotExistsException.class)
    public ResponseEntity<Error> handleEventDoesNotExistsException(EventDoesNotExistsException ex) {
        return new ResponseEntity<>(new Error(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

}
