package dev.aequitas.boot.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class EventStoreRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public EventStoreRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Long getNextId() {
        return jdbcTemplate.getJdbcOperations().queryForObject("call next value for seq_eventstore", Long.class);
    }

    public List<Event> getAll() {
        System.out.println("Getting all events!");

        RowMapper<Event> mapper = (rs, rowNum) -> {
            Event event = new Event();
            event.setId(rs.getLong("id"));
            event.setEventName(rs.getString("event_name"));
            event.setDate(rs.getObject("date", LocalDateTime.class));
            event.setUuid(rs.getString("uuid"));
            event.setPayload(rs.getString("payload"));
            return event;
        };

        return jdbcTemplate.query("select * from eventstore", mapper);
    }

    public void create(Event event) {
        String query = "insert into eventstore (id, uuid, date, event_name, payload) values (:id, :uuid, :date, :eventName, :payload)";
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", getNextId())
                .addValue("uuid", UUID.randomUUID().toString().substring(0, 32))
                .addValue("date", LocalDateTime.now())
                .addValue("eventName", event.getEventName())
                .addValue("payload", event.getPayload());

        this.jdbcTemplate.update(query, map);
    }
}
