package events.project.events.project.service;


import events.project.model.Event;
import events.project.model.EventType;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

    Event findById(Long id);

    List<Event> findByName(String name);

    void saveEvent(Event event);

    void updateEvent(Event event);

    void deleteEventById(Long id);

    void deleteAllEvents();

    List<Event> findAll();

    boolean isEventExist(Event event);

    List<Event> findByEventType(EventType eventType);

    List<Event> findByDate(LocalDate date);

    List<Event> findByDateBetween(LocalDate date1, LocalDate date2);
}
