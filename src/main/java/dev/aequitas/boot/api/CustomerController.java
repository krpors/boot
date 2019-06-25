package dev.aequitas.boot.api;

import dev.aequitas.boot.events.CustomerCreated;
import dev.aequitas.boot.events.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerRepository esr;

    public CustomerController(final CustomerRepository esr) {
        this.esr = esr;
    }

    public void post(CustomerCreated )
}
