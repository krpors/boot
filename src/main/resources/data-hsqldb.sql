insert into eventstore values (
      next value for seq_eventstore      -- id
    , '5ed4b1ab61891eba3b7a6743eeda87aa' -- uuid
    , current_timestamp                  -- timestamp
    , 'OrderCreated'                     -- eventname
    , 'Some json payload?'               -- payload
);
