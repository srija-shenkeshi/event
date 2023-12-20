package com.calendar.event.repository;

import com.calendar.event.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    Optional<List<Event>> findByEventName(String eventName);
    Optional<List<Event>> findByEventDate(String eventDate);
    Optional<Event> findByEventId(String eventId);
    void deleteByEventId(String eventId);
}
