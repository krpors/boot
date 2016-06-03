package wtf.cruft.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Component;
import wtf.cruft.Schedule;

@Component
@ManagedResource(
        objectName = "wtf.cruft:name=Resource",
        description = "This is an example managed resource. Check me out in VisualVM.")
public class Resource {

    private static final Logger log = LoggerFactory.getLogger(Resource.class);

    private int number;

    private Schedule schedule;

    @Autowired
    public Resource(Schedule schedule) {
        this.schedule = schedule;
    }

    @ManagedAttribute(description = "desc for getter")
    public int getNumber() {
        return number;
    }

    @ManagedAttribute(description = "desc for setter")
    public void setNumber(int num) {
        this.number = num;
    }

    @ManagedOperation(description = "Some managed operation. Invoke me!")
    @ManagedOperationParameter(name = "value", description = "Description of this arbitrary value")
    @ManagedMetric(category = "CRUFT")
    public void someOperation(String value) {
        log.info("Some operation has been invoked with value '{}'", value);
        schedule.setEnabled(!schedule.isEnabled());
    }
}
