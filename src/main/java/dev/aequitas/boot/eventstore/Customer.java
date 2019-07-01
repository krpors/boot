package dev.aequitas.boot.eventstore;

import dev.aequitas.boot.eventstore.event.CustomerCreatedEvent;
import dev.aequitas.boot.eventstore.event.CustomerDeactivatedEvent;
import dev.aequitas.boot.eventstore.event.CustomerModifiedEvent;
import dev.aequitas.boot.eventstore.event.Event;

/**
 * Aggregate root for a customer.
 */
public class Customer {

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

    public void applyEvent(Event e) throws Exception {
        if (e instanceof CustomerCreatedEvent) {
            applyCustomerCreatedEvent((CustomerCreatedEvent) e);
        } else if (e instanceof CustomerModifiedEvent) {
            applyCustomerModifiedEvent((CustomerModifiedEvent) e);
        } else if (e instanceof CustomerDeactivatedEvent) {
            applyCustomerDeactivatedEvent((CustomerDeactivatedEvent) e);
        } else {
            throw new Exception("Unhandled event of type " + e.getClass());
        }
    }

    private void applyCustomerCreatedEvent(final CustomerCreatedEvent event) {
        this.setName(event.getCustomerName());
    }

    private void applyCustomerModifiedEvent(final CustomerModifiedEvent event) throws Exception {
        if (!this.active) {
            throw new Exception("Customer is marked inactive, cannot do that, Dave.");
        }

        this.setName(event.getCustomerName());
        this.setSomeOtherProperty(event.getSomeRandomProperty());
    }

    private void applyCustomerDeactivatedEvent(final CustomerDeactivatedEvent event) {
        this.setActive(false);
    }
}
