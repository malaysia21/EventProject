package events.project.services;


import events.project.modelDto.EventDto;
import events.project.modelEntity.User;

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

    void acceptEvent(Long id);

}
