package events.project.controller;


import events.project.model.Event;
import events.project.model.EventDto;
import events.project.model.EventSearching;
import events.project.other.CustomErrorType;
import events.project.service.EventServiceImpl;
import events.project.service.UserService;
import events.project.specification.EventSpecification;
import events.project.validation.ValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EventController {

    private final EventServiceImpl eventService;
    private final UserService userService;

    @Autowired
    public EventController(EventServiceImpl eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorType eventNotFound(EventNotFoundException e) {
        Long eventId = e.getEventId();
        return new CustomErrorType("Event with id " + eventId + " not found");
    }

    @ExceptionHandler(EventExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CustomErrorType eventExist(EventExistException e) {
        String eventName = e.getEvent().getName();
        return new CustomErrorType("Unable to create. Event with name " +
                eventName + " already exist.");
    }

    /**
     * Pobranie wszytskich wydarzen
     *
     * @return lista wydarzen, status odpowiedzi
     */

    @GetMapping(value = "/allEvents")
    public ResponseEntity getEvents() {
        List<EventDto> eventList = eventService.findAll();
        if (eventList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(eventList);
    }

    /**
     * Pobranie wszytskich zatwierdzonych wydarzen
     *
     * @return lista wydarzen, status odpowiedzi
     */

    @GetMapping(value = "/allConfirmedEvents")
    public ResponseEntity getConfirmedEvents() {
        List<EventDto> eventList = eventService.findByConfirmIsTrue();
        if (eventList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(eventList);
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

    @ResponseBody
    @GetMapping(value = "/event/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EventDto getEvent(@PathVariable Long id) {
        return eventService.findById(id);
    }

    /**
     * Utworzenie nowego wydarzenia
     *
     * @param event wydarzenie
     * @return header nowego wydarzenia, status odpowiedzi
     */

    @PostMapping(value = "/addEvent")
    public ResponseEntity addEvent(@Valid @RequestBody EventDto event, BindingResult result, Principal user) {
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

    @PutMapping(value = "/updateEvent/{id}")
    public ResponseEntity updateEvent(@PathVariable("id") Long id, @Valid @RequestBody EventDto event, BindingResult result) {
        EventDto currentEvent = eventService.findById(id);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        }
        if (currentEvent == null) {
            throw new EventNotFoundException(id);
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
            throw new EventNotFoundException(id);
        }
        eventService.deleteEventById(id);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
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
            throw new EventNotFoundException(id);
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
    public ResponseEntity<?> search(@Valid @RequestBody EventSearching eventSearching, BindingResult result) {
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



