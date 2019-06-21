package dev.aequitas.boot.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedule {

    private static final Logger log = LoggerFactory.getLogger(Schedule.class);

    /**
     * Value should be read at runtime/initialization by reading a .properties file.
     */
    @Value("${scheduling.enabled}")
    private boolean enabled = true;

    @Scheduled(fixedRate = 60000)
    public void report() {
        // Hacky. Timer is run at all times, but nothing is done.
        if (enabled) {
            log.info("Reporting for duty");
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        log.info("Scheduling enabled: {}", enabled);
    }
}
