package dev.aequitas.boot.eventstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aequitas.boot.eventstore.event.EventRecord;
import dev.aequitas.boot.eventstore.presentation.CreateCustomerCommand;
import dev.aequitas.boot.eventstore.presentation.DeactivateCustomerCommand;
import dev.aequitas.boot.eventstore.presentation.ModifyCustomerCommand;
import dev.aequitas.boot.eventstore.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    private final ObjectMapper mapper;

    @Autowired
    public CustomerController(CustomerService customerService, final ObjectMapper mapper) {
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EventRecord> getAllEvents() {
        return customerService.getAllCustomers();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, headers = "type=CreateCustomerCommand")
    public void createCustomer(@RequestBody CreateCustomerCommand create) throws Exception {
        customerService.createCustomer(create);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, headers = "type=ModifyCustomerCommand")
    public void modifyCustomer(@RequestBody ModifyCustomerCommand modify) throws Exception {
        customerService.modifyCustomer(modify);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, headers = "type=DeactivateCustomerCommand")
    public void deactivate(final DeactivateCustomerCommand event) {

    }
}
