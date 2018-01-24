package events.project.other;

import events.project.model.Event;
import events.project.model.EventDto;

public class EventToEventDtoMapper implements Mapper<Event, EventDto> {
    @Override
    public EventDto map(Event event)  {
        if(event==null){
            //throw new EventNotFoundException(event.getId());
            return null;
        }

        return EventDto.New().name(event.getName()).
                eventType(event.getEventType()).point(event.getPoint()).
                address(event.getAddress()).startingTime(event.getStartingTime()).
        endingTime(event.getEndingTime()).date(event.getDate()).userId(event.getUser().getId()).confirm(event.isConfirm()).build();
    }
}
