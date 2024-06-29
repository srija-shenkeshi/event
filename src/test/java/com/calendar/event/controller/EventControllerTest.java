package com.calendar.event.controller;

import com.calendar.event.model.CreateEventRequest;
import com.calendar.event.model.Event;
import com.calendar.event.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventService eventService;

    private CreateEventRequest createEventRequest;

    String createEventRequestJson;
    Event event;
    List<Event> eventList = new ArrayList<>();

    @BeforeEach
    void setup() throws JsonProcessingException {
        createEventRequest = CreateEventRequest.builder()
                .eventName("BIRTHDAY")
                .eventDate(LocalDate.now().toString())
                .description("Vishal's Birthday")
                .location("Bangalore")
                .build();

        createEventRequestJson = objectMapper.writeValueAsString(createEventRequest);
        event = new Event("123456","BIRTHDAY","2024-05-17","Vishal's Birthday","Bangalore");
        eventList.add(event);


    }

    @Test
    @DisplayName("Create Event 201 status check")
    void givenCreateEventRequest_whenCallCreateEvent_thenReturnEvent() throws Exception {
        //given
        given(eventService.createEvent(any(CreateEventRequest.class))).willReturn(event);

        //when
        ResultActions response = mockMvc.perform(post("/createEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createEventRequestJson));

        //then
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.eventId", is(event.getEventId())))
                .andExpect(jsonPath("$.eventName", is(event.getEventName())));

    }

    @Test
    @DisplayName("Create Event eventName validation check")
    void givenCreateEventRequest_whenCallCreateEvent_thenReturn() throws Exception {
        //given
        createEventRequest.setEventDate("1995-05-17");
        createEventRequestJson = objectMapper.writeValueAsString(createEventRequest);
        given(eventService.createEvent(any(CreateEventRequest.class))).willReturn(event);

        //when
        ResultActions response = mockMvc.perform(post("/createEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createEventRequestJson));

        //then
        response.andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Get Event by name 200 status check")
    void givenEventName_whenCallGetEventByName_thenReturnEvent() throws Exception {
        //given
        given(eventService.getEventListByName(any(String.class))).willReturn(eventList);

        //when
        ResultActions response = mockMvc.perform(get("/getEventByName/{eventName}", "BIRTHDAY"));

        //then
        response.andExpect(status().isOk());

    }

    @Test
    @DisplayName("Get Event by name 404 status check")
    void givenEventName_whenCallGetEventByName_thenReturnNotFoundError() throws Exception {
        //given
        given(eventService.getEventListByName(any(String.class))).willReturn(new ArrayList<>());

        //when
        ResultActions response = mockMvc.perform(get("/getEventByName/{eventName}", "BIRTHDAY"));

        //then
        response.andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Get Event by date 200 status check")
    void givenEventDate_whenCallGetEventByName_thenReturnEvent() throws Exception {
        //given
        given(eventService.getEventListByDate(any(String.class))).willReturn(eventList);

        //when
        ResultActions response = mockMvc.perform(get("/getEventByDate/{eventDate}", "2024-05-17"));

        //then
        response.andExpect(status().isOk());

    }

    @Test
    @DisplayName("Get Event by date 404 status check")
    void givenEventDate_whenCallGetEventByDate_thenReturnNotFoundError() throws Exception {
        //given
        given(eventService.getEventListByDate(any(String.class))).willReturn(new ArrayList<>());

        //when
        ResultActions response = mockMvc.perform(get("/getEventByDate/{eventDate}", "2024-05-17"));

        //then
        response.andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Edit Event 200 status check")
    void givenEditEventRequest_whenCallEditEvent_thenReturnEvent() throws Exception {
        //given
        given(eventService.editEvent(any(CreateEventRequest.class),any(String.class))).willReturn(event);

        //when
        ResultActions response = mockMvc.perform(put("/editEvent/{eventId}","123456")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createEventRequestJson));

        //then
        response.andExpect(status().isOk());

    }

    @Test
    @DisplayName("Edit Event 404 status check")
    void givenEditEventRequest_whenCallEditEvent_thenReturnNotFoundError() throws Exception {
        //given
        given(eventService.editEvent(any(CreateEventRequest.class),any(String.class))).willReturn(null);

        //when
        ResultActions response = mockMvc.perform(put("/editEvent/{eventId}","123456")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createEventRequestJson));

        //then
        response.andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Delete event 204 status check")
    void givenEventId_whenCallDeleteEvent_thenReturnSuccess() throws Exception {
        //given
        given(eventService.getEventById(any(String.class))).willReturn(Optional.ofNullable(event));
        given(eventService.deleteEvent(any(String.class))).willReturn(true);

        //when
        ResultActions response = mockMvc.perform(delete("/deleteEvent/{eventId}", "123456"));

        //then
        response.andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Delete event 404 status check")
    void givenEventId_whenCallDeleteEvent_thenReturnNotFoundError() throws Exception {
        //given
        given(eventService.getEventById(any(String.class))).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(delete("/deleteEvent/{eventId}", "123456"));

        //then
        response.andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Delete event 404 status check")
    void givenEventId_whenCallDeleteEvent_thenReturnNotFound() throws Exception {
        //given
        given(eventService.getEventById(any(String.class))).willReturn(Optional.ofNullable(event));
        given(eventService.deleteEvent(any(String.class))).willReturn(false);

        //when
        ResultActions response = mockMvc.perform(delete("/deleteEvent/{eventId}", "123456"));

        //then
        response.andExpect(status().isNotFound());

    }
}
