package dev.aequitas.boot.eventstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aequitas.boot.eventstore.CustomerRepository;
import dev.aequitas.boot.eventstore.event.CustomerCreatedEvent;
import dev.aequitas.boot.eventstore.event.EventRecord;
import dev.aequitas.boot.eventstore.presentation.CreateCustomerCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerRepository customerRepository;

    private final ObjectMapper mapper;

    public CustomerController(final CustomerRepository esr, final ObjectMapper mapper) {
        this.customerRepository = esr;
        this.mapper = mapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EventRecord> getAllEvents() {
        return customerRepository.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createCustomer(@RequestBody CreateCustomerCommand create) throws Exception {

        // This command leads to one event. Since this is the first event to create a customer,
        // a new UUID is generated.
        UUID uuid = UUID.randomUUID();

        CustomerCreatedEvent event = new CustomerCreatedEvent();
        event.setCustomerName(create.getName());

        EventRecord record = new EventRecord();
        record.setEventName("CustomerCreated");
        record.setUuid(uuid.toString());
        record.setPayload(mapper.writeValueAsString(event));

        customerRepository.persist(record);;
    }
}
