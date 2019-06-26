package dev.aequitas.boot.eventstore;

/**
 * Aggregate root for a customer.
 */
public class Customer {

    private boolean active;

    private String name;

    private String someOtherProperty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSomeOtherProperty() {
        return someOtherProperty;
    }

    public void setSomeOtherProperty(String someOtherProperty) {
        this.someOtherProperty = someOtherProperty;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
