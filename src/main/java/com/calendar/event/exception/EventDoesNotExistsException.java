package com.calendar.event.exception;

public class EventDoesNotExistsException extends Exception {
    public EventDoesNotExistsException(String msg) {
        super(msg);
    }
}
