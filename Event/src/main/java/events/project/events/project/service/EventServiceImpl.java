package events.project.events.project.service;


import events.project.model.*;
import events.project.other.EventSearchingToEventRequestMapper;
import events.project.repositories.EventRepository;
import events.project.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service("eventService")
@Transactional
public class EventServiceImpl implements  EventService{


    private EventRepository eventRepository;

    private EventSearchingToEventRequestMapper mapper = new EventSearchingToEventRequestMapper();

    @Autowired
    public EventServiceImpl(EventRepository er){
    this.eventRepository=er;
}

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public List<Event> findByName(String name) {
        return eventRepository.findByNameIgnoreCase(name);
    }

    @Override
    public void saveEvent(User user, EventDto eventDto) {
        Event event = mapper.map(eventDto);
        event.setUser(user);
        eventRepository.save(event);
    }

   // @Override
//    public void updateEvent(Event event) {
//        saveEvent(event);
//    }

    @Override
    public void deleteEventById(Long id) {
        eventRepository.delete(id);
    }

    @Override
    public void deleteAllEvents() {
        eventRepository.deleteAll();
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public boolean isEventExist(Event event) {
        for (Event e: eventRepository.findAll())
              if (event.getName().equals(e.getName())){
                  return true;}
       return false;
    }

    public List<Event> findByEventType(EventType eventType){
        return eventRepository.findByEventType(eventType);
    };

    public List<Event> findByDate(LocalDate date){

        return eventRepository.findByDate(date);
    };

    public List<Event> findByDateBetween(LocalDate date1, LocalDate date2){
        return eventRepository.findByDateBetween(date1,date2);
    };

    public List<Event> findAll(Specification<Event> eventSpecification1) {
        return eventRepository.findAll(eventSpecification1);
    }

    public void acceptEvent(Long id) {
        Event event = eventRepository.findById(id);
        event.setConfirm(true);
    }
}
