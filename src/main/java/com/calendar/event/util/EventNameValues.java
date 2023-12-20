package com.calendar.event.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventNamesValidator.class)
public @interface EventNameValues {
    String message() default "Invalid value. Allowed values are {allowedValues}";

    String[] allowedValues();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
