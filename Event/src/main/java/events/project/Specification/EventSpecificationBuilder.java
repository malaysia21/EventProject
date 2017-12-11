package events.project.Specification;

import events.project.model.Event;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;

public class EventSpecificationBuilder {
    private final List<SearchCriteria> params;

    public EventSpecificationBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public EventSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Event> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Event>> specs = new ArrayList<Specification<Event>>();
        for (SearchCriteria param : params) {
            specs.add(new EventSpecification(param));
        }

        Specification<Event> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }
}