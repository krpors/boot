package dev.aequitas.boot.eventstore.event;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EventMapper {

    private final Map<String, Class> map;

    public EventMapper() {
        this.map = new HashMap<>();

        map.put("CustomerCreatedEvent", CustomerCreatedEvent.class);
//        map.put("CustomerDeactivatedEvent")
    }
}
