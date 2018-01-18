package events.project.events.project.service;


import events.project.model.*;
import events.project.other.EventDtoToEventMapper;
import events.project.other.EventToEventDtoMapper;
import events.project.repositories.EventRepository;
import events.project.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service("eventService")
@Transactional
public class EventServiceImpl implements  EventService{


    private EventRepository eventRepository;

    private EventDtoToEventMapper toEntity = new EventDtoToEventMapper();
    private EventToEventDtoMapper toDto = new EventToEventDtoMapper();
    @Autowired
    public EventServiceImpl(EventRepository er){
    this.eventRepository=er;
}

    @Override
    public EventDto findById(Long id) {
        return toDto.map(eventRepository.findById(id));
    }


    @Override
    public void saveEvent(User user, EventDto eventDto) {
        Event event = toEntity.map(eventDto);
        event.setUser(user);
        eventRepository.save(event);
    }

    @Override
    public EventDto updateEvent(EventDto event) {
        Event eventEntity = toEntity.map(event);
        Event save = eventRepository.save(eventEntity);
        EventDto eventDto = toDto.map(save);
        return eventDto;
    }

    @Override
    public void deleteEventById(Long id) {
        eventRepository.delete(id);
    }


    @Override
    public List<EventDto> findAll() {
        List<EventDto> list = new ArrayList<>();
        for (Event e :eventRepository.findAll()) {
            list.add(toDto.map(e));
        }
        return list;
    }

    @Override
    public List<EventDto> findByConfirmIsTrue() {
        List<EventDto> list = new ArrayList<>();
        for (Event e :eventRepository.findByConfirmIsTrue()) {
            list.add(toDto.map(e));
        }
        return list;
    }

    @Override
    public List<EventDto> findByConfirmIsFalse() {
        List<EventDto> list = new ArrayList<>();
        for (Event e :eventRepository.findByConfirmIsFalse()) {
            list.add(toDto.map(e));
        }
        return list;
    }


    public boolean isEventExist(EventDto event) {
        Event eventEntity = toEntity.map(event);
        Event eventById = eventRepository.findByName(eventEntity.getName());

        if(eventById==null)
              {return false;}
       else {return true;}
    }

    @Override
    public List<EventDto> findByUser(Long id) {
        List<EventDto> list = new ArrayList<>();
        for (Event e :eventRepository.findByUserId(id)) {
            list.add(toDto.map(e));
        }
        return list;
    }


    public List<EventDto> findAllWithCriteria(Specification<Event> eventSpecification) {
        List<EventDto> list = new ArrayList<>();
        for (Event e :eventRepository.findAll(eventSpecification)) {
            list.add(toDto.map(e));
        }
        return list;
    }

    public void acceptEvent(Long id) {
        Event event = eventRepository.findById(id);
        event.setConfirm(true);
    }
}
