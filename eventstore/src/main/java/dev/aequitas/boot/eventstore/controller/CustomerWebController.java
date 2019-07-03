package dev.aequitas.boot.eventstore.controller;

import dev.aequitas.boot.eventstore.Customer;
import dev.aequitas.boot.eventstore.presentation.CreateCustomerCommand;
import dev.aequitas.boot.eventstore.presentation.DeactivateCustomerCommand;
import dev.aequitas.boot.eventstore.presentation.ModifyCustomerCommand;
import dev.aequitas.boot.eventstore.repository.EventRecord;
import dev.aequitas.boot.eventstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/eventstore")
public class CustomerWebController {

    private final CustomerService customerService;

    @Autowired
    public CustomerWebController(
            final CustomerService customerService,
            final ApplicationContext context) {
        this.customerService = customerService;
    }

    @RequestMapping
    public String eventStore(Model model) {
        List<EventRecord> allEvents = customerService.getAllCustomers();
        model.addAttribute("createCommand", new CreateCustomerCommand());
        model.addAttribute("modifyCommand", new ModifyCustomerCommand());
        model.addAttribute("deactivateCommand", new DeactivateCustomerCommand());
        model.addAttribute("customer", new Customer());
        model.addAttribute("events", allEvents);

        return "eventstore";
    }

    // https://spring.io/guides/gs/handling-form-submission/
    @PostMapping(path = "/create")
    public String eventStoreCreate(@ModelAttribute CreateCustomerCommand command, Model model) throws Exception {
        customerService.createCustomer(command);
        return eventStore(model);
    }

    @PostMapping(path = "/modify")
    public String eventStoreModify(@ModelAttribute ModifyCustomerCommand command, Model model) throws Exception {
        customerService.modifyCustomer(command);
        return eventStore(model);
    }

    @PostMapping(path = "/deactivate")
    public String eventStoreDeactivate(@ModelAttribute DeactivateCustomerCommand command, Model model) throws Exception {
        customerService.deactivateCustomer(command);
        return eventStore(model);
    }

    @GetMapping(path = "/replay")
    public String eventStoreReplay(@RequestParam("uuid") String uuid, Model model) throws Exception {
        Customer c = customerService.replayForUuid(uuid);
        model.addAttribute("customer", c);
        return "customer";
    }

}
