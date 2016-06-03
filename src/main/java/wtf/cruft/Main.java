package wtf.cruft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Main {

    /**
     * Logger, uses logback internally.
     */
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // No banner please.
        new SpringApplicationBuilder().
                bannerMode(Banner.Mode.OFF).
                sources(Main.class).
                run(args);
    }
}
