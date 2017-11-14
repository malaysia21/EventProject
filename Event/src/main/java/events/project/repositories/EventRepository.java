package events.project.repositories;


import events.project.model.Event;
import events.project.model.EventType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    List<Event> findAll();
    List<Event> findByNameIgnoreCase(String name);
    Event findById(Long id);
    List<Event> findByEventType(EventType eventType);
    List<Event> findByDate(LocalDate date);
    List<Event> findByDateBetween(LocalDate date1, LocalDate date2);


}