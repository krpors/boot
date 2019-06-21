package dev.aequitas.boot.api;

import dev.aequitas.boot.constraints.PartyConstraint;

import javax.validation.constraints.NotNull;

/**
 * Simple pojo.
 */
public class Greeting {

    @NotNull
    private String name;

    private String message;

    @PartyConstraint
    private String party;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }


    @Override
    public String toString() {
        return String.format("%s, %s, %s", name, message, party);
    }
}
