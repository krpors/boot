package dev.aequitas.boot.eventstore.event;

/**
 * Event that has happened when a customer is modified.
 */
public class CustomerModifiedEvent implements Event {

    private String customerName;

    private String someRandomProperty;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSomeRandomProperty() {
        return someRandomProperty;
    }

    public void setSomeRandomProperty(String someRandomProperty) {
        this.someRandomProperty = someRandomProperty;
    }
}
