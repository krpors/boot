package dev.aequitas.boot.events;

import java.time.LocalDateTime;

public abstract class AbstractEvent {

    private Long id;

    private String uuid;

    private LocalDateTime date;

    private String eventName;

    private String payload;

    public AbstractEvent() {

    }
}
