package dev.aequitas.boot.eventstore.event;

import org.springframework.context.event.EventListener;

/**
 * JSON serialized event, as an entry in the database.
 */
public class CustomerCreatedEvent implements Event {

    private String customerName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
