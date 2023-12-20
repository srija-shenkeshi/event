package com.calendar.event.service;

import com.calendar.event.model.CreateEventRequest;
import com.calendar.event.model.Event;
import com.calendar.event.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class EventService {

    @Autowired
    EventRepository eventRepository;
    public Event createEvent(CreateEventRequest createEventRequest) {
        String eventId = UUID.randomUUID().toString();

        //build event object based on the createEventRequest
        Event event = new Event(eventId,createEventRequest.getEventName(), createEventRequest.getEventDate(),
                createEventRequest.getDescription(), createEventRequest.getLocation());

        // save the Event object to database
        Event savedEvent = eventRepository.insert(event);
        log.info("Saved Event Details: {}", savedEvent);
        return savedEvent;
    }

    public List<Event> getEventListByName(String eventName) {
        Optional<List<Event>> eventList = eventRepository.findByEventName(eventName);
        return eventList.orElse(Collections.emptyList());
    }

    public List<Event> getEventListByDate(String eventDate) {
        Optional<List<Event>> eventList = eventRepository.findByEventDate(eventDate);
        return eventList.orElse(Collections.emptyList());
    }

    public Optional<Event> getEventById(String eventId) {
        return eventRepository.findByEventId(eventId);
    }

    public Event editEvent(CreateEventRequest createEventRequest, String eventId) {
        Event existingEvent = eventRepository.findByEventId(eventId).orElse(null);
        if (existingEvent != null) {
            existingEvent.setEventName(createEventRequest.getEventName());
            existingEvent.setEventDate(createEventRequest.getEventDate());
            existingEvent.setDescription(createEventRequest.getDescription());
            existingEvent.setLocation(createEventRequest.getLocation());
            return eventRepository.save(existingEvent);
        }
        return null;
    }

    public boolean deleteEvent(String eventId) {
        eventRepository.deleteByEventId(eventId);
        return true;
    }
}
