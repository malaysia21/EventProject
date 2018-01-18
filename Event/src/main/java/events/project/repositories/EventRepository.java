package events.project.repositories;


import events.project.model.Event;
import events.project.model.EventType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findAll();
    Event findById(Long id);
    Event save(Event event);
    List<Event> findByConfirmIsTrue();
    List<Event> findByConfirmIsFalse();
    Event findByName(String name);
    List<Event> findByUserId(Long id);


}
