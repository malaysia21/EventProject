package events.project.controller;

import events.project.model.Event;

public class EventExistException extends RuntimeException {

    private Event event;

    public EventExistException(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

}
