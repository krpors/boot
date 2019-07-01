package dev.aequitas.boot.eventstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aequitas.boot.eventstore.Customer;
import dev.aequitas.boot.eventstore.CustomerRepository;
import dev.aequitas.boot.eventstore.event.CustomerCreatedEvent;
import dev.aequitas.boot.eventstore.event.CustomerModifiedEvent;
import dev.aequitas.boot.eventstore.event.Event;
import dev.aequitas.boot.eventstore.event.EventRecord;
import dev.aequitas.boot.eventstore.presentation.CreateCustomerCommand;
import dev.aequitas.boot.eventstore.presentation.ModifyCustomerCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, headers = "type=CreateCustomerCommand")
    public void createCustomer(@RequestBody CreateCustomerCommand create) throws Exception {

        // This command leads to one event. Since this is the first event to create a customer,
        // a new UUID is generated.
        UUID uuid = UUID.randomUUID();

        CustomerCreatedEvent event = new CustomerCreatedEvent();
        event.setCustomerName(create.getName());

        EventRecord record = new EventRecord();
        record.setUuid(uuid.toString());
        record.setEvent(event);

        customerRepository.persist(record);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, headers = "type=ModifyCustomerCommand")
    public void modifyCustomer(@RequestBody ModifyCustomerCommand modify) throws Exception {
        if (StringUtils.isEmpty(modify.getUuid())) {
            throw new Exception("Need a customer UUID to modify");
        }

        // Get all previous records belonging to that UUID
        List<EventRecord> records = customerRepository.getAllWithUuid(modify.getUuid());
        if (records.isEmpty()) {
            throw new Exception(String.format("UUID '%s' is not found in the event store", modify.getUuid()));
        }

        // Create a new customer object, build it from the ground up.
        Customer c = new Customer();
        for (EventRecord r : records) {
            String classId = r.getClassId();
            @SuppressWarnings("unchecked")
            Class<Event> clazz = (Class<Event>) Class.forName(classId);
            Event theEvent = mapper.readValue(r.getPayload(), clazz);
            c.applyEvent(theEvent);
        }

        // Map a new event from the command, and then apply it.
        CustomerModifiedEvent event = new CustomerModifiedEvent();
        event.setCustomerName(modify.getName());
        event.setSomeRandomProperty(modify.getSomeProperty());

        // TODO: throw invalid event or something if we cannot apply the event, for instance
        // when modifying a customer when they have been set inactive is an invalid event.
        c.applyEvent(event);

        EventRecord record = new EventRecord();
        record.setUuid(modify.getUuid());
        record.setEvent(event);

        customerRepository.persist(record);

        System.out.println(c.getName());
    }
}
