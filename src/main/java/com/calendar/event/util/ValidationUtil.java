package com.calendar.event.util;

import com.calendar.event.exception.BusinessRuleValidationFailedException;
import com.calendar.event.model.CreateEventRequest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ValidationUtil {

    public void validateCreateEventRequest(CreateEventRequest createEventRequest) throws BusinessRuleValidationFailedException {
        StringBuilder stringBuilder = new StringBuilder();
        boolean isEDatePast = isEventDateIsPastDate(createEventRequest.getEventDate());
        if (isEDatePast) {
            stringBuilder.append("Past dates are not allowed");
        }
        String validationErrorMsg = stringBuilder.toString();
        if (!validationErrorMsg.isEmpty()) {
            throw new BusinessRuleValidationFailedException(stringBuilder.toString());
        }
    }

    private boolean isEventDateIsPastDate(String date) {
        if (date != null && !date.isEmpty()) {
            LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate inputDate = LocalDate.parse(date, dtf);
            return inputDate.isBefore(localDate);
        }
        return false;
    }

}
