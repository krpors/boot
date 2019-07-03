package dev.aequitas.boot.eventstore;

import dev.aequitas.boot.eventstore.event.CustomerCreatedEvent;
import dev.aequitas.boot.eventstore.event.CustomerDeactivatedEvent;
import dev.aequitas.boot.eventstore.event.CustomerModifiedEvent;
import dev.aequitas.boot.eventstore.event.EventException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Simple test class to tes the customer and the event appliance.
 */
public class CustomerTest {

    @Test
    public void stuff() throws EventException {
        Customer c = new Customer();

        {
            CustomerCreatedEvent event = new CustomerCreatedEvent();
            event.setCustomerName("Veritas");
            c.applyEvent(event);
        }
        {
            CustomerModifiedEvent event = new CustomerModifiedEvent();
            event.setCustomerName("Aequitas");
            c.applyEvent(event);
        }
        {
            CustomerDeactivatedEvent event = new CustomerDeactivatedEvent();
            c.applyEvent(event);
        }

        Assert.assertEquals("Aequitas", c.getName());

        try {
            CustomerModifiedEvent event = new CustomerModifiedEvent();
            event.setCustomerName("Something new");
            c.applyEvent(event);
            Assert.fail("A modification cannot happen when the customer has been deactivated");
        } catch (EventException ex) {
            // pass
        }
    }
}
