package dev.aequitas.boot.events;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String uuid;
    // Note: mybatis allows us to use LocalDate here, by HSQLDB stores it as something like 51443-12-25 00:00 instead.
    // Making it a LocalDateTime (since the datatype in SQL is also declared as a datetime) fixes this.
    private LocalDateTime date;
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
        this.uuid = uuid.substring(0, 32);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
