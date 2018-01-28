package events.project.exceptions;

/**
 * Wyjątek - wydarzenie o danym idetyfikatorze nie istnieje
 * @version 1.1
 */
public class EventNotFoundException extends RuntimeException {

    private Long eventId;
    public EventNotFoundException(Long eventId) {
        this.eventId = eventId;
    }
    public Long getEventId() {
        return eventId;
    }
}
