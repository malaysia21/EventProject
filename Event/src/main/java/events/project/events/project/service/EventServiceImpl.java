package events.project.events.project.service;


import events.project.model.Event;
import events.project.model.EventType;
import events.project.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service("eventService")
@Transactional
public class EventServiceImpl implements  EventService{


    private EventRepository eventRepository;

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
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    @Override
    public void updateEvent(Event event) {
        saveEvent(event);
    }

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
}
