package wtf.cruft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedule {

    private static final Logger log = LoggerFactory.getLogger(Schedule.class);

    @Scheduled(fixedRate = 5000)
    public void report() {
        log.info("Reporting for duty");
    }
}
