package dev.aequitas.boot.eventstore.event;

/**
 * Can occur when applying an {@link Event} fails.
 */
public class EventException extends Exception {

    private final Event event;

    public EventException(final Event event, String reason) {
        super(String.format("Unable to process event '%s': %s", event.getClass().getName(), reason));
        this.event = event;
    }
    public Event getEvent() {
        return event;
    }
}
