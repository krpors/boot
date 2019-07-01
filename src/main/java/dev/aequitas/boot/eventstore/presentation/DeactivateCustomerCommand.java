package dev.aequitas.boot.eventstore.presentation;

/**
 * JSON command which leads to the deactivation of a Customer.
 */
public class DeactivateCustomerCommand {

    private String uuid;

    // no other properties necessary


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
