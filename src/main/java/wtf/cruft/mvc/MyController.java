package wtf.cruft.mvc;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Controller
public class MyController {

    @RequestMapping(name = "/")
    public String example(Model model) {
        model.addAttribute("name", "mimi");

        List<String> entities = Arrays.asList("One", "Two", "Trio", "Pterodactylus");
        model.addAttribute("entities", entities);

        return "index";
    }

    @RequestMapping("/properties")
    @ResponseBody
    public Properties props() {
        return System.getProperties();
    }
}
