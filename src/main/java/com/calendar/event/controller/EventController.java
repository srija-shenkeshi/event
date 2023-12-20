package com.calendar.event.controller;

import com.calendar.event.exception.BusinessRuleValidationFailedException;
import com.calendar.event.exception.CreateEventRequestRejectedexception;
import com.calendar.event.exception.EventDoesNotExistsException;
import com.calendar.event.model.CreateEventRequest;
import com.calendar.event.model.Event;
import com.calendar.event.service.EventService;
import javax.validation.Valid;

import com.calendar.event.util.ValidationUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class EventController {

    @Autowired
    EventService eventService;

    ValidationUtil validationUtil = new ValidationUtil();

    @SneakyThrows
    @PostMapping(path = "/createEvent")
    public ResponseEntity<Event> createEvent(@Valid @RequestBody CreateEventRequest createEventRequest) {
        log.info("Received request to create an event: {}", createEventRequest);
        try {
            //validating CreateEventRequest.
            validationUtil.validateCreateEventRequest(createEventRequest);
        } catch (BusinessRuleValidationFailedException e) {
            log.error("Create Event Request validation failed with Reason, Request: {}", createEventRequest, e);
            throw new CreateEventRequestRejectedexception("Rejected create event request. Reason: ".concat(e.getMessage()));
        }
        Event event = eventService.createEvent(createEventRequest);
        return new ResponseEntity<>(event, new HttpHeaders(), HttpStatus.CREATED);
    }

    @SneakyThrows
    @GetMapping(path = "/getEventByName/{eventName}")
    public ResponseEntity<List<Event>> getEventByName(@Valid @PathVariable String eventName) {
        log.info("Get Event by Event Name : {}", eventName);
        List<Event> eventList = eventService.getEventListByName(eventName);
        if (eventList.isEmpty()) {
            throw new EventDoesNotExistsException("Event details not found");
        } else {
            return new ResponseEntity<>(eventList, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @SneakyThrows
    @GetMapping(path = "/getEventByDate/{eventDate}")
    public ResponseEntity<List<Event>> getEventByDate(@Valid @PathVariable String eventDate) {
        log.info("Get Event by Event Date : {}", eventDate);
        List<Event> eventList = eventService.getEventListByDate(eventDate);
        if (eventList.isEmpty()) {
            throw new EventDoesNotExistsException("Event details not found");
        } else {
            return new ResponseEntity<>(eventList, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @SneakyThrows
    @PutMapping(path = "/editEvent/{eventId}")
    public ResponseEntity<Event> editEvent(@Valid @PathVariable String eventId, @RequestBody CreateEventRequest createEventRequest) {
        log.info("Received request to edit an event: {} {}", eventId, createEventRequest);
        Event updatedEvent = eventService.editEvent(createEventRequest, eventId);
        if (updatedEvent == null){
            throw new EventDoesNotExistsException("Event details not found");
        } else {
            return new ResponseEntity<>(updatedEvent, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @SneakyThrows
    @DeleteMapping(path = "/deleteEvent/{eventId}")
    public ResponseEntity<Void> deleteEvent(@Valid @PathVariable String eventId) {
        log.info("Received request to delete an event: {}", eventId);
        Optional<Event> event = eventService.getEventById(eventId);
        if (event.isPresent()) {
            boolean deleted = eventService.deleteEvent(eventId);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            throw new EventDoesNotExistsException("Event details not found");
        }
    }
}
