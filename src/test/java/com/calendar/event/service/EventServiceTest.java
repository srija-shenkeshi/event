package com.calendar.event.service;

import com.calendar.event.model.CreateEventRequest;
import com.calendar.event.model.Event;
import com.calendar.event.repository.EventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    CreateEventRequest createEventRequest;
    Event event;
    List<Event> eventList;

    @BeforeEach
    void setup() throws JsonProcessingException {
        createEventRequest = CreateEventRequest.builder()
                .eventName("BIRTHDAY")
                .eventDate("2024-05-17")
                .description("Vishal's Birthday")
                .location("Bangalore")
                .build();

        event = new Event("123456","BIRTHDAY","2024-05-17","Vishal's Birthday","Bangalore");
        eventList = new ArrayList<>();
        eventList.add(event);
    }

    @Test
    void givenCreateEventRequest_whenCallCreateEvent_thenReturnEvent() {
        //given
        given(eventRepository.insert(any(Event.class))).willReturn(event);

        //when
        Event response = eventService.createEvent(createEventRequest);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getEventId()).isNotNull();
        assertThat(response.getEventId()).isNotEmpty();
        assertThat(response.getEventName()).isNotNull();
        assertThat(response.getEventName()).isNotEmpty();

    }

    @Test
    void givenEventName_whenCallGetEventByName_thenReturnEvent() {
        //given
        String eventName = "BIRTHDAY";
        given(eventRepository.findByEventName(eventName)).willReturn(Optional.ofNullable(eventList));

        //when
        List<Event> response = eventService.getEventListByName(eventName);

        //then
        assertThat(response).isNotNull();
    }

    @Test
    void givenEventDate_whenCallGetEventByName_thenReturnEvent() {
        //given
        String eventDate = "2024-05-17";
        given(eventRepository.findByEventDate(eventDate)).willReturn(Optional.ofNullable(eventList));

        //when
        List<Event> response = eventService.getEventListByDate(eventDate);

        //then
        assertThat(response).isNotNull();
    }

    @Test
    void givenEventId_whenCallGetEventByEventId_thenReturnEvent() {
        //given
        String eventId = "123456";
        given(eventRepository.findByEventId(eventId)).willReturn(Optional.ofNullable(event));

        //when
        Optional<Event> response = eventService.getEventById(eventId);

        //then
        assertThat(response).isNotNull();
    }

    @Test
    void givenEditEventRequest_whenCallEditEvent_thenReturnEvent() {
        //given
        String eventId = "123456";
        given(eventRepository.findByEventId(eventId)).willReturn(Optional.ofNullable(event));
        given(eventRepository.save(any(Event.class))).willReturn(event);

        //when
        Event response = eventService.editEvent(createEventRequest,eventId);

        //then
        assertThat(response).isNotNull();
    }

    @Test
    void givenEditEventRequest_whenCallEditEvent_thenReturnNull() {
        //given
        String eventId = "123456";
        given(eventRepository.findByEventId(eventId)).willReturn(Optional.empty());

        //when
        Event response = eventService.editEvent(createEventRequest,eventId);

        //then
        assertThat(response).isNull();
    }
}
