package events.project.other;

import events.project.model.Event;
import events.project.model.EventDto;

public class EventDtoToEventMapper implements Mapper<EventDto,Event> {
    @Override
    public Event map(EventDto eventRequest) {
        return Event.New().name(eventRequest.getName()).
                eventType(eventRequest.getEventType()).point(eventRequest.getPoint()).
                address(eventRequest.getAdress()).startingTime(eventRequest.getStartingTime()).
        endingTime(eventRequest.getEndingTime()).date(eventRequest.getDate()).bulid();
    }
}
