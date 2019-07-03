package dev.aequitas.boot.eventstore;

import dev.aequitas.boot.eventstore.event.*;

/**
 * Aggregate root for a customer.
 */
public class Customer implements AggregateRoot {

    private boolean active;

    private String uuid;

    private String name;

    private String someOtherProperty;

    public Customer() {
        setActive(true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSomeOtherProperty() {
        return someOtherProperty;
    }

    public void setSomeOtherProperty(String someOtherProperty) {
        this.someOtherProperty = someOtherProperty;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public void applyEvent(final Event e) throws EventException {
        if (e instanceof CustomerCreatedEvent) {
            applyCustomerCreatedEvent((CustomerCreatedEvent) e);
        } else if (e instanceof CustomerModifiedEvent) {
            applyCustomerModifiedEvent((CustomerModifiedEvent) e);
        } else if (e instanceof CustomerDeactivatedEvent) {
            applyCustomerDeactivatedEvent((CustomerDeactivatedEvent) e);
        } else {
            throw new EventException(e, "Unhandled event of type " + e.getClass());
        }
    }

    private void applyCustomerCreatedEvent(final CustomerCreatedEvent event) {
        this.setName(event.getCustomerName());
    }

    private void applyCustomerModifiedEvent(final CustomerModifiedEvent event) throws EventException {
        if (!this.active) {
            throw new EventException(event, "Customer is marked inactive, cannot do that, Dave.");
        }

        this.setName(event.getCustomerName());
        this.setSomeOtherProperty(event.getSomeRandomProperty());
    }

    private void applyCustomerDeactivatedEvent(final CustomerDeactivatedEvent event) {
        this.setActive(false);
    }
}
