package dev.aequitas.boot.eventstore.presentation;

/**
 * JSON command which leads to the creation of a customer.
 */
public class CreateCustomerCommand {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
