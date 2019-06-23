package dev.aequitas.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class Main {

    /**
     * Logger, uses logback internally.
     */
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private final EventStoreMapper mapper;

    @Autowired
    public Main(EventStoreMapper mapper) {
        this.mapper = mapper;

        List<Event> all = this.mapper.getAll();
        System.out.println(all);

        Event one = this.mapper.findById(1L);
        System.out.println(one);

        Event nevent = new Event();
        nevent.setUuid(UUID.randomUUID().toString());
        nevent.setEventName("Test Event");
        nevent.setDate(LocalDateTime.now());
        nevent.setPayload("Some payload");
        this.mapper.addEvent(nevent);
    }

    public static void main(String[] args) {
        // Can be used to turn off the banner and other things.
        new SpringApplicationBuilder()
                .sources(Main.class)
                .run(args);
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
