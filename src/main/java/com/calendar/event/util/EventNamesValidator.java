package com.calendar.event.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EventNamesValidator implements ConstraintValidator<EventNameValues, String> {

    private String[] allowedValues;

    @Override
    public void initialize(EventNameValues constraintAnnotation) {
        this.allowedValues = constraintAnnotation.allowedValues();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // Null values are not valid
        }

        for (String allowedValue : allowedValues) {
            if (allowedValue.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
