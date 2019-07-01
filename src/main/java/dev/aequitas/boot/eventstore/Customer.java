package dev.aequitas.boot.eventstore;

import dev.aequitas.boot.eventstore.event.CustomerCreatedEvent;
import dev.aequitas.boot.eventstore.event.CustomerModifiedEvent;
import dev.aequitas.boot.eventstore.event.Event;

/**
 * Aggregate root for a customer.
 */
public class Customer {

    private boolean active;

    private String name;

    private String someOtherProperty;

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

    public void applyEvent(Event e) {
        if (e instanceof CustomerCreatedEvent) {
            CustomerCreatedEvent customerCreatedEvent = (CustomerCreatedEvent) e;
            applyCustomerCreatedEvent(customerCreatedEvent);
        } else if (e instanceof CustomerModifiedEvent) {
            CustomerModifiedEvent customerModifiedEvent = (CustomerModifiedEvent) e;
            applyCustomerModifiedEvent(customerModifiedEvent);
        }
    }

    private void applyCustomerCreatedEvent(final CustomerCreatedEvent event) {
        this.setName(event.getCustomerName());
    }

    private void applyCustomerModifiedEvent(final CustomerModifiedEvent event) {
        this.setName(event.getCustomerName());
        this.setSomeOtherProperty(event.getSomeRandomProperty());
    }
}
