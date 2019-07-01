package dev.aequitas.boot.mvc;


import dev.aequitas.boot.api.Greeting;
import dev.aequitas.boot.eventstore.Customer;
import dev.aequitas.boot.eventstore.event.EventRecord;
import dev.aequitas.boot.eventstore.presentation.CreateCustomerCommand;
import dev.aequitas.boot.eventstore.presentation.DeactivateCustomerCommand;
import dev.aequitas.boot.eventstore.presentation.ModifyCustomerCommand;
import dev.aequitas.boot.eventstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Controller
public class MyController {

    private final CustomerService customerService;

    @Autowired
    public MyController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Controller entry for the landing page.
     *
     * @param model The Spring MVC model.
     * @return The view name for Spring MVC, in this case a Thymeleaf template.
     */
    @RequestMapping(path = "/")
    public String index(Model model) {
        model.addAttribute("name", "mimi");

        List<String> entities = Arrays.asList("One", "Two", "Trio", "Pterodactylus");
        model.addAttribute("entities", entities);

        return "index";
    }

    @RequestMapping(path = "/hello")
    public String hello(@RequestParam("name") String name, Model model) {
        Greeting g = new Greeting();
        g.setName(name);
        g.setMessage(String.format("Hello to the world, from %s!", name));

        model.addAttribute("name", name); // bind 'name' with string
        model.addAttribute("greeting", g);  // bind 'greeting' with a full blown pojo, for rendering in 'hello.html'.

        return "hello";
    }

    @RequestMapping(path = "/eventstore")
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
    @PostMapping(path = "/eventstore/create")
    public String eventStoreCreate(@ModelAttribute CreateCustomerCommand command, Model model) throws Exception {
        customerService.createCustomer(command);
        return eventStore(model);
    }

    @PostMapping(path = "/eventstore/modify")
    public String eventStoreModify(@ModelAttribute ModifyCustomerCommand command, Model model) throws Exception {
        customerService.modifyCustomer(command);
        return eventStore(model);
    }

    @PostMapping(path = "/eventstore/deactivate")
    public String eventStoreDeactivate(@ModelAttribute DeactivateCustomerCommand command, Model model) throws Exception {
        customerService.deactivateCustomer(command);
        return eventStore(model);
    }

    @GetMapping(path = "/eventstore/replay")
    public String eventStoreReplay(@RequestParam("uuid") String uuid,  Model model) throws Exception {
        Customer c = customerService.replayForUuid(uuid);
        model.addAttribute("customer", c);
        return "customer";
    }

    @RequestMapping(path = "/properties")
    @ResponseBody
    public Properties props() {
        return System.getProperties();
    }
}
