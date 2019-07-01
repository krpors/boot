package dev.aequitas.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aequitas.boot.eventstore.event.CustomerCreatedEvent;
import dev.aequitas.boot.eventstore.event.Event;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

/**
 * Generic fiddling around test class.
 */
public class GenericTest {
    @Test
    public void casting() throws IOException, ClassNotFoundException {
        String c = "dev.aequitas.boot.eventstore.event.CustomerCreatedEvent";

        CustomerCreatedEvent event = new CustomerCreatedEvent();
        event.setCustomerName("Kevin");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(event);
        System.out.println(json);

        // Now read it back, with casting?
        @SuppressWarnings("unchecked")
        Class<Event> clazz = (Class<Event>) Class.forName(c);

        Event e = mapper.readValue(json, clazz);
        if (e instanceof CustomerCreatedEvent) {
            CustomerCreatedEvent customerCreatedEvent = (CustomerCreatedEvent) e;
            System.out.println(customerCreatedEvent.getCustomerName());
        }
    }

    @Test
    public void uuid() {
        System.out.println(UUID.randomUUID().toString());
    }
}
