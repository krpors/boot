package dev.aequitas.boot.eventstore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aequitas.boot.eventstore.Customer;
import dev.aequitas.boot.eventstore.CustomerRepository;
import dev.aequitas.boot.eventstore.event.*;
import dev.aequitas.boot.eventstore.presentation.CreateCustomerCommand;
import dev.aequitas.boot.eventstore.presentation.DeactivateCustomerCommand;
import dev.aequitas.boot.eventstore.presentation.ModifyCustomerCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    private final ObjectMapper mapper;

    @Autowired
    public CustomerService(final CustomerRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void createCustomer(final CreateCustomerCommand create) throws Exception {
        // This command leads to one event. Since this is the first event to create a customer,
        // a new UUID is generated.
        UUID uuid = UUID.randomUUID();

        CustomerCreatedEvent event = new CustomerCreatedEvent();
        event.setCustomerName(create.getName());

        EventRecord record = new EventRecord();
        record.setUuid(uuid.toString());
        record.setEvent(event);

        repository.persist(record);
    }

    public void modifyCustomer(final ModifyCustomerCommand modify) throws Exception {
        Customer c = replayForUuid(modify.getUuid());

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

        repository.persist(record);

        System.out.println(c.getName());
    }

    public void deactivateCustomer(final DeactivateCustomerCommand deactivate) throws Exception {
        Customer c = replayForUuid(deactivate.getUuid());

        CustomerDeactivatedEvent event = new CustomerDeactivatedEvent();
        c.applyEvent(event);

        EventRecord record = new EventRecord();
        record.setUuid(deactivate.getUuid());
        record.setEvent(event);

        repository.persist(record);
    }

    public Customer replayForUuid(String uuid) throws Exception {
        if (StringUtils.isEmpty(uuid)) {
            throw new Exception("Need a customer UUID to modify");
        }

        // Get all previous records belonging to that UUID
        List<EventRecord> records = repository.getAllWithUuid(uuid);
        if (records.isEmpty()) {
            throw new Exception(String.format("UUID '%s' is not found in the event store", uuid));
        }

        // Create a new customer object, build it from the ground up.
        Customer c = new Customer();
        c.setUuid(uuid);
        for (EventRecord r : records) {
            String classId = r.getClassId();
            @SuppressWarnings("unchecked")
            Class<Event> clazz = (Class<Event>) Class.forName(classId);
            Event theEvent = mapper.readValue(r.getPayload(), clazz);
            c.applyEvent(theEvent);
        }

        return c;
    }

    public List<EventRecord> getAllCustomers() {
        return repository.getAll();
    }
}
