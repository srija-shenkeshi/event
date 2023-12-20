package com.calendar.event.model;

import com.calendar.event.util.EventNameValues;
import com.calendar.event.util.ISODateFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEventRequest {
    @EventNameValues(allowedValues = {"ANNIVERSARY", "MEETING","BIRTHDAY","REMINDER","APPOINTMENT","DEADLINE","TRAVEL_PLAN"})
    String eventName;
    @ISODateFormat
    String eventDate;
    @NotEmpty
    @Size(max = 200, message = "Maximum length of 200")
    String description;
    String location;

}
