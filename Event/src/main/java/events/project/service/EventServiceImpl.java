package events.project.service;


import events.project.model.*;
import events.project.other.EventDtoToEventMapper;
import events.project.other.EventToEventDtoMapper;
import events.project.repositories.AddressRepository;
import events.project.repositories.EventRepository;
import events.project.repositories.PointRepository;
import events.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("eventService")
@Transactional
public class EventServiceImpl implements  EventService{

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PointRepository pointRepository;


    private EventDtoToEventMapper toEntity = new EventDtoToEventMapper();

    private EventToEventDtoMapper toDto = new EventToEventDtoMapper();

    @Autowired
    public EventServiceImpl(EventRepository er, AddressRepository ar, PointRepository pr){
    this.eventRepository=er;
    this.addressRepository =ar;
    this.pointRepository=pr;

}

    @Override
    public EventDto findById(Long id) {
        return toDto.map(eventRepository.findById(id));
    }


    @Override
    public EventDto saveEvent(User user, EventDto eventDto) {
        Event event = toEntity.map(eventDto);
        event.setUser(user);

        Address address = addressRepository.checkIfExist(eventDto.getAddress().getCity(),
                eventDto.getAddress().getStreet(),
                eventDto.getAddress().getNumber());

        if (address ==null){
            event.setAddress(eventDto.getAddress());
        }
        else event.setAddress(address);

        Point point = pointRepository.checkIfExist(eventDto.getPoint().getLongitude(),
                eventDto.getPoint().getLatitude());

        if (point==null){
            event.setPoint(eventDto.getPoint());
        }
        else event.setPoint(point);



        Event save = eventRepository.save(event);
        EventDto eventDtoReturned = toDto.map(save);
        return eventDtoReturned;
    }

    @Override
    public EventDto updateEvent(Long id, EventDto eventDto) {
        Event event = eventRepository.findById(id);

        Address address = addressRepository.checkIfExist(eventDto.getAddress().getCity(),
                eventDto.getAddress().getStreet(),
                eventDto.getAddress().getNumber());

        if (address ==null){
            event.setAddress(eventDto.getAddress());
        }
        else event.setAddress(address);

        Point point = pointRepository.checkIfExist(eventDto.getPoint().getLongitude(),
                eventDto.getPoint().getLatitude());

        if (point==null){
            event.setPoint(eventDto.getPoint());
        }
        else event.setPoint(point);

        event.setName(eventDto.getName());
        event.setEventType(eventDto.getEventType());
        event.setDate(eventDto.getDate());
        event.setStartingTime(eventDto.getStartingTime());
        event.setEndingTime(eventDto.getEndingTime());


        Event saveEvent = eventRepository.save(event);
        EventDto eventDtoRetun = toDto.map(saveEvent);
        return eventDtoRetun;
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
