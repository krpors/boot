package dev.aequitas.boot.eventstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aequitas.boot.eventstore.event.EventRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerRepository(
            final NamedParameterJdbcTemplate jdbcTemplate,
            final ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    private Long getNextId() {
        return jdbcTemplate.getJdbcOperations().queryForObject("call next value for seq_customer_event_id", Long.class);
    }

    public void persist(final EventRecord event) throws Exception {

        String sql = "insert into customer (event_id, uuid, timestamp, event_name, class_id, payload)"
                + " values (:eventid, :uuid, :timestamp, :eventname, :classId, :payload)";

        MapSqlParameterSource src = new MapSqlParameterSource()
                .addValue("eventid", getNextId())
                .addValue("uuid", event.getUuid())
                .addValue("timestamp", LocalDateTime.now())
                .addValue("eventname", event.getEvent().getClass().getSimpleName())
                .addValue("classId", event.getEvent().getClass().getName())
                .addValue("payload", objectMapper.writeValueAsString(event.getEvent()));

        int i = jdbcTemplate.update(sql, src);
        System.out.printf("Updated %d records\n", i);
    }

    public List<EventRecord> getAll() {
        String query = "select * from customer";
        return get(query, new MapSqlParameterSource());
    }

    public List<EventRecord> getAllWithUuid(String uuid) {
        String sql = "select * from customer where uuid = :uuid order by event_id";
        MapSqlParameterSource src = new MapSqlParameterSource("uuid", uuid);
        return get(sql, src);
    }

    private List<EventRecord> get(String query, SqlParameterSource params) {
        return jdbcTemplate.query(query, params, (rs, rowNum) -> {
            EventRecord e = new EventRecord();
            e.setId(rs.getLong("event_id"));
            e.setUuid(rs.getString("uuid"));
            e.setDatetime(rs.getObject("timestamp", LocalDateTime.class));
            e.setEventName(rs.getString("event_name"));
            e.setClassId(rs.getString("class_id"));
            e.setPayload(rs.getString("payload"));
            return e;
        });
    }
}
