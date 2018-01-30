package events.project.controllers;

import events.project.exceptions.EventExistException;
import events.project.exceptions.EventNotFoundException;
import events.project.modelDto.EventDto;
import events.project.modelDto.EventSearching;
import events.project.modelEntity.User;
import events.project.specification.EventSpecification;
import events.project.services.UserService;
import events.project.validation.ValidationErrorBuilder;
import events.project.services.EventServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller dla klasy Event
 * @version 1.1
 */
@Log4j2
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EventController {
    private final EventServiceImpl eventService;
    private final UserService userService;

    @Autowired
    public EventController(EventServiceImpl es, UserService us) {
        this.eventService = es;
        this.userService = us;
    }

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void eventNotFound(EventNotFoundException e) {
        Long eventId = e.getEventId();
        log.debug("Event with id " + eventId + " not found");
    }

    @ExceptionHandler(EventExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void eventExist(EventExistException e) {
        String eventName = e.getEvent().getName();
        LocalDateTime startingDateTime = e.getEvent().getBeginningDateTime();
        log.debug("Unable to create. Event with name " + eventName + " and date of launch " + startingDateTime + " already exist.");
    }

    /**
     * Pobranie wszytskich wydarzeń
     * @return lista wydarzenń
     */
    @GetMapping(value = "/allEvents")
    public ResponseEntity getEvents() {
        List<EventDto> eventList = eventService.findAll();
        if (eventList.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(eventList);
    }

    /**
     * Pobranie wszytskich zatwierdzonych wydarzeń
     * @return lista wydarzeń
     */
    @GetMapping(value = "/allConfirmedEvents")
    public ResponseEntity getConfirmedEvents() {
        List<EventDto> eventList = eventService.findByConfirmIsTrue();
        if (eventList.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(eventList);
    }

    /**
     * Pobranie wszytskich niezatwierdzonych wydarzeń
     * @return lista wydarzeń
     */
    @GetMapping(value = "/allNotConfirmedEvents")
    public ResponseEntity getNotConfirmedEvents() {
        List<EventDto> eventList = eventService.findByConfirmIsFalse();
        if (eventList.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(eventList);
    }

    /**
     * Pobranie wydarzeń utworzonych przez danego użytkownika
     * @param user Principal user
     * @return lista wydarzen
     */
    @GetMapping(value = "/userEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getuserEvents(Principal user) {
        User userByEmail = userService.findByEmail(user.getName());
        List<EventDto> eventList = eventService.findByUser(userByEmail.getId());
        if (eventList.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(eventList);
    }

    /**
     * Pobranie informacji o danym wydarzeniu
     * @param id identyfikator wydarzenia
     * @return wydarzenie
     */
    @ResponseBody
    @GetMapping(value = "/event/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));}

    /**
     * Utworzenie nowego wydarzenia
     * @param event wydarzenie
     * @param result BindingResult
     * @return błędy po walidacji przekazanego ciała wydarzenia/nowe wydarzenie
     */
    @PostMapping(value = "/addEvent", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addEvent(@Valid @RequestBody EventDto event, BindingResult result, Principal user) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        EventDto eventDto = eventService.saveEvent(userService.findByEmail(user.getName()), event);
        return ResponseEntity.ok(eventDto);
    }

    /**
     * Modyfikacja wydarzenia
     * @param event wydarzenie
     * @param id idetyfikator wydarzenia
     * @param result BindingResult
     * @return błędy po walidacji przekazanego ciała wydarzenia/zmodyfikowane wydarzenie
     */
    @PutMapping(value = "/updateEvent/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateEvent(@PathVariable("id") Long id, @Valid @RequestBody EventDto event, BindingResult result) {
        EventDto currentEvent = eventService.findById(id);
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        EventDto eventDto = eventService.updateEvent(id, event);
        return ResponseEntity.ok(eventDto);
    }

    /**
     * Usuwanie wydarzenia przez administratora
     * @throws EventNotFoundException
     * @param id idetyfikator wydarzenia
     */
    @DeleteMapping(value = "/admin/deleteEvent/{id}", produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json, application/xml")
    public ResponseEntity deleteEvent(@PathVariable("id") long id) {
        if (eventService.findById(id) == null) {
            throw new EventNotFoundException(id);}
        eventService.deleteEventById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Zatwierdzenie wydarzenia przed administratora
     * @throws EventNotFoundException
     * @param id idetyfikator wydarzenia
     */
    @GetMapping(value = "/admin/acceptEvent/{id}")
    public ResponseEntity acceptEvent(@PathVariable("id") long id) {
        if (eventService.findById(id) == null) {
            {throw new EventNotFoundException(id);}}
        eventService.acceptEvent(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Wyszukiwanie wydarzenia
     * @param eventSearching kryteria wyszukiwania wydarzenia
     * @param result BindingResult
     * @return błędy po walidacji przekazanego ciała zapytania/lista wydarzen spelniajaca kryteria
     */
    @PostMapping(value = "/search")
    public ResponseEntity<?> search(@Valid @RequestBody EventSearching eventSearching, BindingResult result
    ) {if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));}
        List<EventDto> allWithCriteria = eventService.findAllWithCriteria(EventSpecification.withDynamicQuery(eventSearching));
        if (allWithCriteria.isEmpty()) {return ResponseEntity.noContent().build();}
        return ResponseEntity.ok(allWithCriteria);
    }
}






