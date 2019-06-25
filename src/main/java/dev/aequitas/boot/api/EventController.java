package dev.aequitas.boot.api;

import dev.aequitas.boot.events.Event;
import dev.aequitas.boot.events.EventStoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    private final EventStoreRepository esr;

    public EventController(final EventStoreRepository esr) {
        this.esr = esr;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getAll() {
        return esr.getAll();
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void create() {
        Event e = new Event();
        e.setEventName("My custom event name " + Math.random() * 1000);
        e.setPayload("Some payload!");
        this.esr.create(e);
    }
}
