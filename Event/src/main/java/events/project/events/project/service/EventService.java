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

    List<EventDto> findByConfirmIsTrue();

    List<EventDto> findByConfirmIsFalse();

    void saveEvent(User user, EventDto eventDto);

    void updateEvent(EventDto event);

    void deleteEventById(Long id);

    boolean isEventExist(EventDto event);

    List<EventDto> findByUser(Long id);


}
