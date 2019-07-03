package dev.aequitas.boot.eventstore.presentation;

/**
 * JSON command which leads to the modification of a Customer.
 */
public class ModifyCustomerCommand {

    private String uuid;

    private String name;

    private String someProperty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSomeProperty() {
        return someProperty;
    }

    public void setSomeProperty(String someProperty) {
        this.someProperty = someProperty;
    }
}
