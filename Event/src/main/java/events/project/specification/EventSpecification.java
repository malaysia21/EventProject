package events.project.specification;

import events.project.model.Event;
import events.project.model.Point;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class EventSpecification {

    public static Specification<Event> withDynamicQuery(String name, String eventType, LocalDate date1, LocalDate date2,
                                                        Point point1, Point point2
    ) {
        return (product, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(builder.and(builder.like(product.get("name"), "%" + name.toLowerCase() + "%")));
            }
            if (eventType != null) {
                predicates.add(builder.and(builder.equal(product.get("eventType"), eventType)));
            }
            if (date1 != null & date2 != null) {
                predicates.add(builder.and(builder.between(product.get("date"), date1, date2)));
            }
            if (point1 != null & point2 != null) {
                predicates.add(builder.and(builder.between(product.get("point").get("longitude"), point1.getLongitude(), point2.getLongitude())));

            }
            if (point1 != null & point2 != null) {
                predicates.add(builder.and(builder.between(product.get("point").get("latitude"), point2.getLatitude(), point1.getLatitude())));

            }
            Predicate[] predicatesArray = new Predicate[predicates.size()];
            return builder.and(predicates.toArray(predicatesArray));
        };
    }

    public static void main(String[] args) {
        log.debug("siemanko, witam");
    }

}