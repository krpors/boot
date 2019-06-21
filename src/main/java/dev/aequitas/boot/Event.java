package dev.aequitas.boot;

import java.io.Serializable;
import java.time.LocalDate;

public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String uuid;
    private LocalDate date;
    private String eventName;
    private String payload;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", date=" + date +
                ", name='" + eventName + '\'' +
                '}';
    }
}
