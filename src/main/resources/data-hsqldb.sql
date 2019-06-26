insert into customer values (
      next value for seq_customer_event_id      -- id
    , 'AA'  -- uuid
    , current_timestamp                         -- timestamp
    , 'CreateCustomer'                          -- eventname
    , 'Some json payload?'                      -- payload
);

insert into customer values (
      next value for seq_customer_event_id      -- id
    , 'AA'                                         -- uuid
    , current_timestamp                         -- timestamp
    , 'ChangeCustomer'                          -- eventname
    , 'Some json payload?'                      -- payload
);


insert into customer values (
      next value for seq_customer_event_id      -- id
    , 'AA'                                         -- uuid
    , current_timestamp                         -- timestamp
    , 'DeactivateCustomer'                      -- eventname
    , 'Some json payload?'                      -- payload
);