package events.project.services;


import events.project.exceptions.EventExistException;
import events.project.exceptions.EventNotFoundException;
import events.project.modelDto.EventDto;
import events.project.modelEntity.Address;
import events.project.modelEntity.Event;
import events.project.modelEntity.Point;
import events.project.mappers.EventDtoToEventMapper;
import events.project.mappers.EventToEventDtoMapper;
import events.project.repositories.AddressRepository;
import events.project.repositories.EventRepository;
import events.project.repositories.PointRepository;
import events.project.modelEntity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Servis dla encji Event
 * @version 1.1
 */
@Service("eventService")
@Transactional
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private AddressRepository addressRepository;
    private PointRepository pointRepository;
    private EventDtoToEventMapper toEntity = new EventDtoToEventMapper();
    private EventToEventDtoMapper toDto = new EventToEventDtoMapper();

    @Autowired
    public EventServiceImpl(EventRepository er, AddressRepository ar, PointRepository pr) {
        this.eventRepository = er;
        this.addressRepository = ar;
        this.pointRepository = pr;

    }

    /**
     * Porbanie wszystkich wydarzeń z repozytorium
     * @return lista wydarzenń
     */
    @Override
    public List<EventDto> findAll() {
        List<EventDto> list = new ArrayList<>();
        for (Event e : eventRepository.findAll()) {
            list.add(toDto.map(e));}
        return list;
    }

    /**
     * Pobranie wszytskich zatwierdzonych wydarzeń z repozytorium
     * @return lista wydarzenń
     */
    @Override
    public List<EventDto> findByConfirmIsTrue() {
        List<EventDto> list = new ArrayList<>();
        for (Event e : eventRepository.findByConfirmIsTrue()) {
            list.add(toDto.map(e));
        }
        return list;
    }

    /**
     * Pobranie wszytskich niezatwierdzonych wydarzeń z repozytorium
     * @return lista wydarzenń
     */
    @Override
    public List<EventDto> findByConfirmIsFalse() {
        List<EventDto> list = new ArrayList<>();
        for (Event e : eventRepository.findByConfirmIsFalse()) {
            list.add(toDto.map(e));
        }
        return list;
    }

    /**
     * Sprawdzenie czy wydarzenie o danej nazwie i dacie rozpoczęcia już istanieje w repozytorium
     * @return true - wydarzenie jest w repozytorium, false - wtdarzenie nie jest w repozytorium
     */
    public boolean isEventExist(EventDto event) {
        Event eventEntity = toEntity.map(event);
        Event eventByNameAndDate = eventRepository.findByNameAndBeginningDateTime(event.getName(), event.getBeginningDateTime());
        if (eventByNameAndDate == null) {
            return false;}
            else {return true;
        }
    }
    /**
     * Pobranie wszystkich wydarzeń utworzonych przez wskazanego użytkownika
     * @param id identyfikator użytkownika
     * @return lista wydarzeń utworzona przez danego użytkownika
     */
    @Override
    public List<EventDto> findByUser(Long id) {
        List<EventDto> list = new ArrayList<>();
        for (Event e : eventRepository.findByUserId(id)) {
            list.add(toDto.map(e));
        }
        return list;
    }

    /**
     * Pobranie wszystkich wydarzeń spełniających zadane kryteria
     * @param eventSpecification kryteria wyszukiwania wydarzeń
     * @return lista wydarzeń spełniająca kryteria
     */
    public List<EventDto> findAllWithCriteria(Specification<Event> eventSpecification) {
        List<EventDto> list = new ArrayList<>();
        for (Event e : eventRepository.findAll(eventSpecification)) {
            list.add(toDto.map(e));
        }
        return list;
    }

    /**
     * Zaakceptowanie wydarzenia przez administratora -
     * ustawienie pola confirm na true oraz pola draggable na false
     * @param id identyfikator wydarzenia
     */
    public void acceptEvent(Long id) {
        Event event = eventRepository.findById(id);
        event.setConfirm(true);
        event.getPoint().setDraggable(false);
    }

    /**
     * Pobranie z repozytorium wydarzenia o zadanym id
     * @param id identyfikator wydarzenia
     * @throws EventNotFoundException
     * @return wydarzenie
     */
    @Override
    public EventDto findById(Long id) {
        return Optional.ofNullable(toDto.map(eventRepository.findById(id)))
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    /**
     * Usuwanie wydarzenia z repozytorium
     * @param id identyfikator wydarzenia
    */
    @Override
    public void deleteEventById(Long id) {
        eventRepository.delete(id);
    }

    /**
     * Zapisanie wydarzenia
     * @param user użtytkownik dodający wydarzenie
     * @param eventDto parametry nowego wydarzenia
     * @throws EventExistException
     * @return dodane do repozytorium wydarzenie
     */
    @Override
    public EventDto saveEvent(User user, EventDto eventDto) {
        if (isEventExist(eventDto)) {throw new EventExistException(eventDto);}
        Event event = toEntity.map(eventDto);
        event.setUser(user);

        Address address = getAddress(eventDto);
        if (address == null) {event.setAddress(eventDto.getAddress());
        } else event.setAddress(address);
        event.getPoint().setDraggable(true);
        Event save = eventRepository.save(event);
        EventDto eventDtoReturned = toDto.map(save);
        return eventDtoReturned;
    }

    /**
     * Modyfikacja wydarzenia
     * @param id identyfikator wydarzenia
     * @param eventDto parametry nowego wydarzenia
     * @throws  EventNotFoundException
     * @return zmodyfikowane wydarzenie
     */
    @Override
    public EventDto updateEvent(Long id, EventDto eventDto) {
        Event event = eventRepository.findById(id);
        if (event==null) {throw new EventNotFoundException(id);}

        Address address = getAddress(eventDto);

        if (address == null) {event.setAddress(eventDto.getAddress());
        } else event.setAddress(address);

        pointRepository.delete(event.getPoint());
        event.setName(eventDto.getName());
        event.setEventType(eventDto.getEventType());
        event.setPoint(eventDto.getPoint());
        event.setBeginningDateTime(eventDto.getBeginningDateTime());
        event.setEndingDateTime(eventDto.getEndingDateTime());

        Event saveEvent = eventRepository.save(event);
        EventDto eventDtoRetun = toDto.map(saveEvent);
        return eventDtoRetun;
    }

    /**
     * Sprawdzenie czy adres juz istenieje w repozytorium
     * @param eventDto parametry wydarzenia
     * @return encja adresu z repozytorium jeżeli istnieje
     */
    public Address getAddress(EventDto eventDto) {
        return addressRepository.checkIfExist(eventDto.getAddress().getCity(),
                eventDto.getAddress().getStreet(),
                eventDto.getAddress().getNumber());
    }

    /**
     * Sprawdzenie czy punkt juz istenieje w repozytorium
     * @param eventDto parametry wydarzenia
     * @return encja punktu z repozytorium jeżeli istnieje
     */
    public Point getPoint(EventDto eventDto) {
        return pointRepository.checkIfExist(eventDto.getPoint().getLongitude(),
                eventDto.getPoint().getLatitude());
    }




}
