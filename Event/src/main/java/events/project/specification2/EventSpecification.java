package events.project.specification2;

import events.project.model.Event;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EventSpecification {

    public static Specification<Event> withDynamicQuery(final String name, String eventType, LocalDate date1, LocalDate date2) {
        return new Specification<Event>() {
            @Override
            public Predicate toPredicate(Root<Event> product, CriteriaQuery<?> query, CriteriaBuilder builder) {

                List<Predicate> predicates = new ArrayList<Predicate>();

                if (name != null) {
                    predicates.add(builder.and(builder.like(product.get(Event_.name), "%" + name.toLowerCase()+ "%")));
                }
                if (eventType != null) {
                    predicates.add(builder.and(builder.equal(product.get(Event_.eventType), eventType)));
                }
                if (date1 != null & date2 !=null) {
                    predicates.add(builder.and(builder.between(product.get(Event_.date), date1, date2)));
                }
                Predicate[] predicatesArray = new Predicate[predicates.size()];
                return builder.and(predicates.toArray(predicatesArray));
            }
        };
    }
}