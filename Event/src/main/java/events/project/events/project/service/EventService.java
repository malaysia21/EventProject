package events.project.events.project.service;


import events.project.model.Event;
import events.project.model.EventDto;
import events.project.model.EventType;
import events.project.users.User;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

    List<EventDto> findAll();
    EventDto findById(Long id);

    List<Event> findByName(String name);

    void saveEvent(User user, EventDto eventDto);

    EventDto updateEvent(EventDto event);

    void deleteEventById(Long id);

    boolean isEventExist(EventDto event);


}
