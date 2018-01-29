package events.project.mappers;

import events.project.modelEntity.Event;
import events.project.modelDto.EventDto;

/**
 * Klasa mapująca obiekt EventDto na obiekt Event
 * @version 1.1
 */
public class EventDtoToEventMapper implements Mapper<EventDto,Event> {
    @Override
    public  Event map(EventDto event) {
    return Event.builder().name(event.getName()).eventType(event.getEventType()).point(event.getPoint()).
            address(event.getAddress()).beginningDateTime(event.getBeginningDateTime()).endingDateTime(event.getEndingDateTime()).build();

    }
}
