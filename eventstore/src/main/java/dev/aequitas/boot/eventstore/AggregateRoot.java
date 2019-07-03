package dev.aequitas.boot.eventstore;

import dev.aequitas.boot.eventstore.event.Event;
import dev.aequitas.boot.eventstore.event.EventException;

/**
 * An Aggregate Root is an object containing all business data, functioning
 * as an 'umbrella'.
 */
public interface AggregateRoot {

    /**
     * Applies the {@link Event} to this aggregate root. Applying an event
     * means that the state of the root changes accordingly.
     *
     * @param event The event to apply.
     * @throws Exception Whenever something fails while trying to apply the event.
     */
    void applyEvent(final Event event) throws EventException;
}
