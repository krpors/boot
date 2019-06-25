package dev.aequitas.boot.events;

/**
 * Aggregate root.
 */
public class Customer {
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
}
