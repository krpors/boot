package dev.aequitas.boot.mvc;


import dev.aequitas.boot.api.Greeting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Controller
@Validated
public class MyController {

    @Value("classpath:zzzbanner.txt")
    @NotNull
    protected Resource query;

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

    @RequestMapping(path = "/properties")
    @ResponseBody
    public Properties props() {
        return System.getProperties();
    }

}
