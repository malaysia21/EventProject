package events.project.mappers;

import events.project.modelEntity.Event;
import events.project.modelDto.EventDto;
/**
 * Klasa mapująca obiekt Event na obiekt EventDto
 * @version 1.1
 */
public class EventToEventDtoMapper implements Mapper<Event, EventDto> {
    @Override
    public EventDto map(Event event) {
        if (event != null) {
            return EventDto.builder().name(event.getName()).
                    eventType(event.getEventType()).point(event.getPoint()).
                    address(event.getAddress()).beginningDateTime(event.getBeginningDateTime()).
                    endingDateTime(event.getEndingDateTime()).userId(event.getUser().getId()).confirm(event.isConfirm()).build();

        } else return null;
    }
}
