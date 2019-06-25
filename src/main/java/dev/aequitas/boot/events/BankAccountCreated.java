package dev.aequitas.boot.events;

public class BankAccountCreated extends AbstractEvent {

    private String iban;
    private String owner;
}
