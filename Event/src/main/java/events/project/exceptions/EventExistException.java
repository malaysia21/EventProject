package events.project.exceptions;

import events.project.modelDto.EventDto;

/**
 * Wyjątek - wydarzenie o takiej nazwie i dacie rozpoczęcia już istnieje
 * @version 1.1
 */
public class EventExistException extends RuntimeException {

    private EventDto event;
    public EventExistException(EventDto event) {
        this.event = event;
    }
    public EventDto getEvent() {
        return event;
    }

}
