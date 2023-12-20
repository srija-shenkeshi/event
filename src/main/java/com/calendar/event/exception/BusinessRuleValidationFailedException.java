package com.calendar.event.exception;

public class BusinessRuleValidationFailedException extends Exception {
    public BusinessRuleValidationFailedException(String errorMsg) {
        super(errorMsg);
    }
}