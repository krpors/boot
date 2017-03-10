package wtf.cruft.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/greeting")
public class GreetingController {

    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    @RequestMapping(method = RequestMethod.GET)
    public Greeting greet(@RequestParam(name = "name") String name) {
        log.info("Greeting: {}", name);
        Greeting g = new Greeting();
        g.setMessage("This is a message. Hello, " + name + "!");
        g.setName(name);
        return g;
    }
}
