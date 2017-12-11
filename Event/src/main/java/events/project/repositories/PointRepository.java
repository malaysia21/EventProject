package events.project.repositories;


import events.project.model.Event;
import events.project.model.EventType;
import events.project.model.Point;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PointRepository extends CrudRepository<Point, Long>, JpaSpecificationExecutor<Point> {

}
