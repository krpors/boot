package dev.aequitas.boot.eventstore;

import dev.aequitas.boot.eventstore.event.EventRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerRepository(
            final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Long getNextId() {
        return jdbcTemplate.getJdbcOperations().queryForObject("call next value for seq_customer_event_id", Long.class);
    }

    public void persist(final EventRecord event) throws Exception {

        String sql = "insert into customer (event_id, uuid, timestamp, event_name, payload)"
                + " values (:eventid, :uuid, :timestamp, :eventname, :payload)";

        MapSqlParameterSource src = new MapSqlParameterSource()
                .addValue("eventid", getNextId())
                .addValue("uuid", event.getUuid())
                .addValue("timestamp", LocalDateTime.now())
                .addValue("eventname", event.getEventName())
                .addValue("payload", event.getPayload());

        int i = jdbcTemplate.update(sql, src);
        System.out.printf("Updated %d records\n", i);
    }

    public List<EventRecord> getAll() {
        String query = "select * from customer";

        List<EventRecord> lsit = jdbcTemplate.query(query, (rs, rowNum) -> {
            EventRecord e = new EventRecord();
            e.setId(rs.getLong("event_id"));
            e.setUuid(rs.getString("uuid"));
            e.setDatetime(rs.getObject("timestamp", LocalDateTime.class));
            e.setEventName(rs.getString("event_name"));
            e.setPayload(rs.getString("payload"));
            return e;
        });

        return lsit;
    }
}
