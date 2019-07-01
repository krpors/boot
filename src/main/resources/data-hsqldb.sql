insert into customer values (
      next value for seq_customer_event_id      -- id
    , '61499eb6-30b0-428a-98c6-af5ee2aea287'    -- uuid
    , current_timestamp                         -- timestamp
    , 'CreateCustomer'                          -- eventname
    , 'dev.aequitas.boot.eventstore.event.CustomerCreatedEvent' -- event name
    , '{"customerName":"Kevin"}'                -- payload
);

--insert into customer values (
--      next value for seq_customer_event_id      -- id
--    , '61499eb6-30b0-428a-98c6-af5ee2aea287'    -- uuid
--    , current_timestamp                         -- timestamp
--    , 'ChangeCustomer'                          -- eventname
--    , 'Some json payload?'                      -- payload
--);
--
--
--insert into customer values (
--      next value for seq_customer_event_id      -- id
--    , '61499eb6-30b0-428a-98c6-af5ee2aea287'    -- uuid
--    , current_timestamp                         -- timestamp
--    , 'DeactivateCustomer'                      -- eventname
--    , 'Some json payload?'                      -- payload
--);