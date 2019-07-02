package dev.aequitas.boot.eventstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aequitas.boot.eventstore.event.EventRecord;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Basic repository for aggregate roots. The idea is that every aggregate root has it's own table.
 */
@Repository
public abstract class AggregateRootRepository {

    /**
     * Used for queries.
     */
    protected final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * To serialize an Event to JSON.
     */
    protected final ObjectMapper objectMapper;

    /**
     * Used for insertion.
     */
    protected final SimpleJdbcInsert simpleInsert;

    /**
     * The table name to operate on.
     */
    protected final String tableName;

    protected AggregateRootRepository(
            final String tableName,
            final NamedParameterJdbcTemplate jdbcTemplate,
            final ObjectMapper objectMapper) {

        this.tableName = tableName;
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
        this.simpleInsert = new SimpleJdbcInsert(this.jdbcTemplate.getJdbcTemplate())
                .withTableName(this.tableName)
                .usingGeneratedKeyColumns("event_id");
    }

    /**
     * Persists an event to the database.
     *
     * @param event The event to persist.
     * @throws Exception
     */
    public void save(final EventRecord event) throws Exception {
        MapSqlParameterSource src = new MapSqlParameterSource()
                .addValue("uuid", event.getUuid())
                .addValue("timestamp", LocalDateTime.now())
                .addValue("event_name", event.getEvent().getClass().getSimpleName())
                .addValue("class_id", event.getEvent().getClass().getName())
                .addValue("payload", objectMapper.writeValueAsString(event.getEvent()));

        Number i = simpleInsert.executeAndReturnKey(src);
        System.out.println("created entry with " + i);
    }

    /**
     * Fetches literally ALL events.
     *
     * @return Every event from the Aggregate Root table.
     */
    public List<EventRecord> getAll() {
        String query = "select * from " + this.tableName;
        return get(query, new MapSqlParameterSource());
    }

    /**
     * Fetches all events from the table name with a certain UUID.
     *
     * @param uuid The UUID to look up.
     * @return The list of {@link EventRecord} objects, or an empty list if none are found.
     */
    public List<EventRecord> getAllWithUuid(String uuid) {
        String sql = "select * from " + this.tableName + " where uuid = :uuid order by event_id";
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
