package events.project.controller;

import events.project.model.Event;
import events.project.model.EventDto;

public class EventExistException extends RuntimeException {

    private EventDto event;

    public EventExistException(EventDto event) {
        this.event = event;
    }

    public EventDto getEvent() {
        return event;
    }

}
