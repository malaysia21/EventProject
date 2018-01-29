package events.project.specification;

import events.project.modelDto.EventSearching;
import events.project.modelEntity.Event;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa tworzy kryteria wyszukiwania wydarzeń w repozytorium
 * @version 1.1
 */
public class EventSpecification {

    public static Specification<Event> withDynamicQuery(EventSearching event                                                       ) {
        return new Specification<Event>() {
            @Override
            public Predicate toPredicate(Root<Event> product,  CriteriaQuery<?> query, CriteriaBuilder builder) {

                List<Predicate> predicates = new ArrayList<Predicate>();

                if (event.getName() != null) {
                    predicates.add(builder.and(builder.like(product.get("name"), "%" + event.getName().toLowerCase()+ "%")));
                }
                if (event.getEventType() != null) {
                    predicates.add(builder.and(builder.equal(product.get("eventType"), event.getEventType())));
                }
                if (event.getDate() != null) {
                    predicates.add(builder.and(builder.lessThanOrEqualTo(product.get("beginningDateTime"), event.getDate())));
                }
                if (event.getDate() != null) {
                    predicates.add(builder.and(builder.greaterThanOrEqualTo(product.get("endingDateTime"), event.getDate())));
                }
                if (event.getPoint1() != null & event.getPoint2() != null) {
                    predicates.add(builder.and(builder.between(product.get("point").get("longitude"), event.getPoint1().getLongitude(), event.getPoint2().getLongitude())));

                }
                if (event.getPoint1() != null & event.getPoint2() != null) {
                    predicates.add(builder.and(builder.between(product.get("point").get("latitude"), event.getPoint2().getLatitude(), event.getPoint1().getLatitude())));

                }
                Predicate[] predicatesArray = new Predicate[predicates.size()];
                for (Predicate p: predicatesArray
                     ) {
                    System.out.println(p);
                }
                return builder.and(predicates.toArray(predicatesArray));
            }
        };
    }
}