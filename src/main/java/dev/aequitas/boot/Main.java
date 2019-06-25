package dev.aequitas.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class Main {

    /**
     * Logger, uses logback internally.
     */
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public Main() {

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
