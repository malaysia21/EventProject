package events.project.controller;


import events.project.events.project.service.AdressServiceImpl;
import events.project.model.*;
import events.project.other.CustomErrorType;
import events.project.events.project.service.PointServiceImpl;
import events.project.specification.EventSpecification;
import events.project.users.User;
import events.project.users.UserService;
import events.project.validation.ValidationErrorBuilder;
import events.project.events.project.service.EventServiceImpl;
import events.project.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@CrossOrigin(maxAge = 3600)
@RestController

public class EventController {


    public static final Logger logger = LoggerFactory.getLogger(EventController.class);


    private EventServiceImpl eventService;
    private PointServiceImpl pointService;
    private AdressServiceImpl adressService;
    private EventDto eventDto;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public EventController(EventServiceImpl es, PointServiceImpl ps, AdressServiceImpl as){
        this.eventService=es;
        this.pointService=ps;
        this.adressService=as;

    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<CustomErrorType> eventNotFound(EventNotFoundException e)
    {
        Long eventId = e.getEventId();
        CustomErrorType error = new CustomErrorType("Event with id " + eventId  + " not found");
        return new ResponseEntity<CustomErrorType>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventExistException.class)
    public ResponseEntity<CustomErrorType> eventExist(EventExistException e)
    {
        String eventName = e.getEvent().getName();
        CustomErrorType error = new CustomErrorType( "Unable to create. Event with name " +
                eventName + " already exist.");
        return new ResponseEntity<CustomErrorType>(error, HttpStatus.CONFLICT);
    }

    /**
     * Pobranie wszytskich wydarzen
     * @return lista wydarzen, status odpowiedzi
     */

    @GetMapping(value = "/allEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDto>> getEvent(){
        List<EventDto> eventList = eventService.findAll();
        if(eventList.isEmpty()){
            return new ResponseEntity<List<EventDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<EventDto>>(eventList, HttpStatus.OK);
    }

    /**
     * Pobranie wydarzenia o danym id
     * @param id  identyfikator wydarzenia
     * @return wydarzenie, status odpowiedzi
     */

    @GetMapping(value="/event/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDto> getEvent(@PathVariable Long id){
        EventDto event = eventService.findById(id);
        if(event==null){throw new EventNotFoundException(id);}
        return new ResponseEntity<EventDto>(event, HttpStatus.OK);
    }
    /**
     * Utworzenie nowego wydarzenia
     * @param event wydarzenie
     * @return header nowego wydarzenia, status odpowiedzi
     */

    @PostMapping(value = "/addEvent", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addEvent(@Valid @RequestBody EventDto event, BindingResult result, UriComponentsBuilder ucBuilder, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        }
        if (eventService.isEventExist(event))
            {throw new EventExistException(event);}

        Principal principal = request.getUserPrincipal();
        eventService.saveEvent(userService.findByEmail(principal.getName()), event);

        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    /**
     * Modyfikacja wydarzenia
     * @param event wydarzenie
     * @param id idetyfikator wydarzenia
     * @return zmodyfikowane wydarzenie, status odpowiedzi
     */

    @PutMapping(value = "/updateEvent/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEvent(@PathVariable("id") long id, @Valid @RequestBody EventDto event, BindingResult result) {
       EventDto currentEvent = eventService.findById(id);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        }
        if (currentEvent == null) {
            {throw new EventNotFoundException(id);}
        }
        currentEvent.setName(event.getName());
        currentEvent.setEventType(event.getEventType());
        currentEvent.setDate(event.getDate());
        currentEvent.setStartingTime(event.getStartingTime());
        currentEvent.setEndingTime(event.getEndingTime());
        currentEvent.setAdress(event.getAdress());
        currentEvent.setPoint(event.getPoint());

        EventDto updateEvent = eventService.updateEvent(currentEvent);
        return new ResponseEntity<EventDto>(updateEvent, HttpStatus.OK);
    }

    /**
     * Usuwanie wydarzenia przez admina
     * @param id idetyfikator wydarzenia
     * @return status odpowiedzi
     */
    @DeleteMapping(value = "/admin/deleteEvent/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json, application/xml" )
    public ResponseEntity<String> deleteEvent(@PathVariable("id") long id) {
        EventDto event = eventService.findById(id);
        if (event == null) {
            {throw new EventNotFoundException(id);}
        }
        eventService.deleteEventById(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    /**
     * Zatwierdzenie wydarzenia przed admina
     * @param id idetyfikator wydarzenia
     * @return status odpowiedzi
     */
    @GetMapping(value = "/admin/acceptEvent/{id}")
    public ResponseEntity<String> acceptEvent(@PathVariable("id") long id) {
        EventDto event = eventService.findById(id);
        if (event == null) {
            {throw new EventNotFoundException(id);}
        }
        eventService.acceptEvent(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    /**
     * Wyszukiwanie wydarzenia
     * @return lista wydarzen spelniajaca kryteria
     */
    @PostMapping( value = "/searchEvent")
    public ResponseEntity<?>search (@Valid @RequestBody EventSearching eventSearching, BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        }

        Specification<Event> eventSpecification = EventSpecification.
                withDynamicQuery(eventSearching.getName(), eventSearching.getEventType(), eventSearching.getDate1(),
                        eventSearching.getDate2(), eventSearching.getP1(), eventSearching.getP());

        List<EventDto> allWithCriteria = eventService.findAllWithCriteria(eventSpecification);

        if(allWithCriteria.isEmpty()){
            return new ResponseEntity<List<EventDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<EventDto>>(allWithCriteria , HttpStatus.OK);


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



