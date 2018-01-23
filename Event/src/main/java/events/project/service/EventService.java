package events.project.service;


import events.project.model.EventDto;
import events.project.model.User;

import java.util.List;

public interface EventService {

    List<EventDto> findAll();
    EventDto findById(Long id);

    List<EventDto> findByConfirmIsTrue();

    List<EventDto> findByConfirmIsFalse();

    EventDto saveEvent(User user, EventDto eventDto);

    EventDto updateEvent(Long id, EventDto eventDto);

    void deleteEventById(Long id);

    boolean isEventExist(EventDto event);

    List<EventDto> findByUser(Long id);


}
