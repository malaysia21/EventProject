package events.project.controller;

public class EventNotFoundException extends RuntimeException {

    private Long eventId;
    public EventNotFoundException() {

    }
    public EventNotFoundException(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }
}
