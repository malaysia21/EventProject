package events.project.controller;


import events.project.events.project.service.AdressServiceImpl;
import events.project.other.CustomErrorType;
import events.project.specification.EventSpecificationBuilder;
import events.project.events.project.service.PointServiceImpl;
import events.project.specification2.EventSpecification;
import events.project.validation.ValidationErrorBuilder;
import events.project.events.project.service.EventServiceImpl;
import events.project.model.Event;
import events.project.model.EventType;
import events.project.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class EventController {


    public static final Logger logger = LoggerFactory.getLogger(EventController.class);


    private EventServiceImpl eventService;
    private PointServiceImpl pointService;
    private AdressServiceImpl adressService;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    public EventController(EventServiceImpl es, PointServiceImpl ps, AdressServiceImpl as){
        this.eventService=es;
        this.pointService=ps;
        this.adressService=as;

    }

    @RequestMapping(value = "/events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Event>> getEvent(){
        List<Event> eventList = eventService.findAll();
        if(eventList.isEmpty()){
            return new ResponseEntity<List<Event>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Event>>(eventList, HttpStatus.OK);


    }

    @RequestMapping(value="/event/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> getEvent(@PathVariable Long id){
        logger.info("Fetching Event with id {}", id);
        Event event = eventService.findById(id);
        if(event==null){
            logger.error("Event with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Event with id " + id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }


    @RequestMapping(value = "/addEvent", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addEvent(@Valid @RequestBody Event event, BindingResult result, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Event : {}", event);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        }
        if (eventService.isEventExist(event)) {
            logger.error("Unable to create. An Event with name {} already exist", event.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. Event with name " +
                    event.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        System.out.println("jjjjjjjjj " + event.getPoint().getLongitude());
        System.out.println("jjjjjjjjj " + event.getPoint().getLatitude());

        eventService.saveEvent(event);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/event/{id}").buildAndExpand(event.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEvent(@PathVariable("id") long id, @RequestBody Event event) {
       Event currentEvent = eventService.findById(id);
        if (currentEvent == null) {
            logger.error("Unable to update. Event with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Event with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        currentEvent.setName(event.getName());
        currentEvent.setEventType(event.getEventType());
        currentEvent.setDate(event.getDate());

        eventService.updateEvent(currentEvent);
        return new ResponseEntity<Event>(currentEvent, HttpStatus.OK);
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEvent(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Event with id {}", id);
        Event event = eventService.findById(id);
        if (event == null) {
            logger.error("Unable to delete. Event with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Event with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        eventService.deleteEventById(id);
        return new ResponseEntity<Event>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/event/", method = RequestMethod.DELETE)
    public ResponseEntity<Event> deleteAllEvents() {
        logger.info("Deleting All Events");
        eventService.deleteAllEvents();
        return new ResponseEntity<Event>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users2")
    @ResponseBody
    public List<Event> search2(@RequestParam (required =false) String name,
                               @RequestParam (required =false) String eventType,
                               @RequestParam  (required =false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date1,
                               @RequestParam  (required =false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date2
    ) {

        Specification<Event> eventSpecification1 = EventSpecification.withDynamicQuery(name, eventType, date1, date2);

        return eventRepository.findAll(eventSpecification1);

    }






 //dodatkowe

    @RequestMapping(value = "getEventByFirstName/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getEventByFirstName(@PathVariable String name){
        List<Event> events = eventService.findByName(name);
        if (events.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
    }

    @RequestMapping(value = "getEventByType/{type}", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getEventByType(@PathVariable EventType type){
        List<Event> events = eventService.findByEventType(type);
        if (events.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
    }


    @RequestMapping(value = "getEventByDate/{date}", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getEventByDate(
            @PathVariable(name = "date")
            @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date){
        List<Event> events = eventService.findByDate(date);
        if (events.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
    }


@RequestMapping(value="new",  method = RequestMethod.POST)
   public ResponseEntity<List<Event>> filtring (@RequestParam (required =false) String name , @RequestParam EventType eventType){
    List<Event> events = eventService.findAll();

    List<Event> collect2 = events.stream().filter(e -> e.getName().equals(name)).filter(e -> e.getEventType().equals(eventType.toString()))
            .collect(Collectors.toList());
    if (collect2.isEmpty()) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<List<Event>>(collect2, HttpStatus.OK);

}
    @RequestMapping(value = "getEventByDateBetween/{date1}/{date2}", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getEventByDateBetween(
            @PathVariable(name = "date1")
            @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date1,
            @PathVariable(name = "date2")
            @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date2){
        List<Event> events = eventService.findByDateBetween(date1, date2);
        if (events.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Event>>(events, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public List<Event> search(@RequestParam(value = "search") String search) {
        EventSpecificationBuilder builder = new EventSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Event> spec = builder.build();
        return eventRepository.findAll(spec);
    }




}



