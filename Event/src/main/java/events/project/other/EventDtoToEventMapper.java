package events.project.other;

import events.project.model.Event;
import events.project.model.EventDto;

public class EventDtoToEventMapper implements Mapper<EventDto,Event> {
    @Override
    public Event map(EventDto event) {
             return Event.New().name(event.getName()).
                eventType(event.getEventType()).point(event.getPoint()).
                address(event.getAddress()).startingTime(event.getStartingTime()).
        endingTime(event.getEndingTime()).date(event.getDate()).bulid();
    }
}
