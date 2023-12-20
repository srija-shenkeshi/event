package com.calendar.event.model;

import com.calendar.event.util.ISODateFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Event {
    String eventId;
    String eventName;
    @ISODateFormat
    String eventDate;
    String description;
    String location;
}
