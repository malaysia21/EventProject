package events.project.specification2;


import events.project.model.Event;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;


@StaticMetamodel(Event.class)
public class Event_ {


    public static volatile SingularAttribute<Event, String> name;
    public static volatile SingularAttribute<Event, String> eventType;
    public static volatile SingularAttribute<Event, LocalDate> date;

}
