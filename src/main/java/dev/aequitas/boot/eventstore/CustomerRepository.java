package dev.aequitas.boot.eventstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * This is a {@link Customer} aggregate root repository, which is basically just
 */
@Repository
public class CustomerRepository extends AggregateRootRepository {

    @Autowired
    public CustomerRepository(
            final NamedParameterJdbcTemplate jdbcTemplate,
            final ObjectMapper objectMapper) {
        super("customer", jdbcTemplate, objectMapper);
    }

}
