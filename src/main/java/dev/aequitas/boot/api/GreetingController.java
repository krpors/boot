package dev.aequitas.boot.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/greeting")
public class GreetingController {

    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    private Validator validator;

    @RequestMapping(method = RequestMethod.GET)
    public Greeting greet(@RequestParam(name = "name") String name) {
        log.info("Greeting: {}", name);
        Greeting g = new Greeting();
        g.setMessage("This is a message. Hello, " + name + "!");
        g.setName(name);

        System.out.println(validator);

        return g;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendGreeting(@RequestBody Greeting greeting) {
        log.info("Sending greeting: {}", greeting);
        Set<ConstraintViolation<Greeting>> v = validator.validate(greeting);
        if (!v.isEmpty()) {
            throw new ConstraintViolationException(v);
        }
        log.info("asda: {}", greeting);
    }

}
