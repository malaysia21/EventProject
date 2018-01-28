package events.project.repositories;


import events.project.modelEntity.Event;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
/**
 * Repozytorium dla encji Event
 * @version 1.1
 */
@Repository
public interface EventRepository extends CrudRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findAll();
    Event findById(Long id);
    Event save(Event event);
    List<Event> findByConfirmIsTrue();
    List<Event> findByConfirmIsFalse();
    Event findByName(String name);
    Event findByNameAndBeginningDateTime(String name, LocalDateTime startingDateTime);
    List<Event> findByUserId(Long id);


}
