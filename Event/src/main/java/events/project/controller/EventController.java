package events.project.controller;


import events.project.model.*;
import events.project.other.CustomErrorType;
import events.project.specification.EventSpecification;
import events.project.service.UserService;
import events.project.validation.ValidationErrorBuilder;
import events.project.service.EventServiceImpl;
import events.project.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@CrossOrigin(maxAge = 3600)
@RestController

public class EventController {

    private EventServiceImpl eventService;


    @Autowired
    private UserService userService;

    @Autowired
    public EventController(EventServiceImpl es) {
        this.eventService = es;
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<CustomErrorType> eventNotFound(EventNotFoundException e) {
        Long eventId = e.getEventId();
        System.out.println(eventId);
        CustomErrorType error = new CustomErrorType("Event with id " + eventId + " not found");
        return new ResponseEntity<CustomErrorType>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventExistException.class)
    public ResponseEntity<CustomErrorType> eventExist(EventExistException e) {
        String eventName = e.getEvent().getName();
        CustomErrorType error = new CustomErrorType("Unable to create. Event with name " +
                eventName + " already exist.");
        return new ResponseEntity<CustomErrorType>(error, HttpStatus.CONFLICT);
    }

    /**
     * Pobranie wszytskich wydarzen
     *
     * @return lista wydarzen, status odpowiedzi
     */

    @GetMapping(value = "/allEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDto>> getEvents() {
        List<EventDto> eventList = eventService.findAll();
        if (eventList.isEmpty()) {
            return new ResponseEntity<List<EventDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<EventDto>>(eventList, HttpStatus.OK);
    }

    /**
     * Pobranie wszytskich zatwierdzonych wydarzen
     *
     * @return lista wydarzen, status odpowiedzi
     */

    @GetMapping(value = "/allConfirmedEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDto>> getConfirmedEvents() {
        List<EventDto> eventList = eventService.findByConfirmIsTrue();
        if (eventList.isEmpty()) {
            return new ResponseEntity<List<EventDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<EventDto>>(eventList, HttpStatus.OK);
    }

    /**
     * Pobranie wszytskich niezatwierdzonych wydarzen
     *
     * @return lista wydarzen, status odpowiedzi
     */

    @GetMapping(value = "/allNotConfirmedEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDto>> getNotConfirmedEvents() {
        List<EventDto> eventList = eventService.findByConfirmIsFalse();
        if (eventList.isEmpty()) {
            return new ResponseEntity<List<EventDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<EventDto>>(eventList, HttpStatus.OK);
    }

    @GetMapping(value = "/userEvents/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDto>> getuserEvents(@PathVariable Long id) {
        List<EventDto> eventList = eventService.findByUser(id);
        if (eventList.isEmpty()) {
            return new ResponseEntity<List<EventDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<EventDto>>(eventList, HttpStatus.OK);
    }

    /**
     * Pobranie wydarzenia o danym id
     *
     * @param id identyfikator wydarzenia
     * @return wydarzenie, status odpowiedzi
     */

    @GetMapping(value = "/event/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDto> getEvent(@PathVariable Long id) {

        EventDto event = eventService.findById(id);
        if (event == null) {
            throw new EventNotFoundException(id);
        }
        return new ResponseEntity<EventDto>(event, HttpStatus.OK);
    }

    /**
     * Utworzenie nowego wydarzenia
     *
     * @param event wydarzenie
     * @return header nowego wydarzenia, status odpowiedzi
     */

    @PostMapping(value = "/addEvent", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addEvent(@Valid @RequestBody EventDto event, BindingResult result, Principal user) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        }
        if (eventService.isEventExist(event)) {
            throw new EventExistException(event);
        }
        EventDto eventDto = eventService.saveEvent(userService.findByEmail(user.getName()), event);
        return new ResponseEntity<EventDto>(eventDto, HttpStatus.CREATED);
    }

    /**
     * Modyfikacja wydarzenia
     *
     * @param event wydarzenie
     * @param id    idetyfikator wydarzenia
     * @return zmodyfikowane wydarzenie, status odpowiedzi
     */

    @PutMapping(value = "/updateEvent/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEvent(@PathVariable("id") Long id, @Valid @RequestBody EventDto event, BindingResult result, Principal user) {
        EventDto currentEvent = eventService.findById(id);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        }
        if (currentEvent == null) {
            {
                throw new EventNotFoundException(id);
            }
        }

        EventDto eventDto = eventService.updateEvent(id, event);

        return new ResponseEntity<EventDto>(eventDto, HttpStatus.OK);
    }

    /**
     * Usuwanie wydarzenia przez admina
     *
     * @param id idetyfikator wydarzenia
     * @return status odpowiedzi
     */
    @DeleteMapping(value = "/admin/deleteEvent/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json, application/xml")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") long id) {
        EventDto event = eventService.findById(id);
        if (event == null) {
            {
                throw new EventNotFoundException(id);
            }
        }
        eventService.deleteEventById(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    /**
     * Zatwierdzenie wydarzenia przed admina
     *
     * @param id idetyfikator wydarzenia
     * @return status odpowiedzi
     */
    @GetMapping(value = "/admin/acceptEvent/{id}")
    public ResponseEntity<String> acceptEvent(@PathVariable("id") long id) {
        EventDto event = eventService.findById(id);
        if (event == null) {
            {
                throw new EventNotFoundException(id);
            }
        }
        eventService.acceptEvent(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    /**
     * Wyszukiwanie wydarzenia
     *
     * @return lista wydarzen spelniajaca kryteria
     */
    @PostMapping(value = "/search")
    public ResponseEntity<?> search(@Valid @RequestBody EventSearching eventSearching, BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        }

        Specification<Event> eventSpecification = EventSpecification.
                withDynamicQuery(eventSearching.getName(), eventSearching.getEventType(), eventSearching.getDate1(),
                        eventSearching.getDate2(), eventSearching.getP1(), eventSearching.getP());

        List<EventDto> allWithCriteria = eventService.findAllWithCriteria(eventSpecification);

        if (allWithCriteria.isEmpty()) {
            return new ResponseEntity<List<EventDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<EventDto>>(allWithCriteria, HttpStatus.OK);


    }


//    @RequestMapping(value="new",  method = RequestMethod.POST)
//   public ResponseEntity<List<Event>> filtring (@RequestParam (required =false) String name , @RequestParam EventType eventType){
//    List<Event> events = eventService.findAll();
//    List<Event> collect2 = events.stream().filter(e -> e.getName().equals(name)).filter(e -> e.getEventType().equals(eventType.toString()))
//            .collect(Collectors.toList());
//    if (collect2.isEmpty()) {
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//    return new ResponseEntity<List<Event>>(collect2, HttpStatus.OK);

//}


}



