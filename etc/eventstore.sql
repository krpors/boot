drop table eventstore if exists;

create table eventstore (
  id         integer
, uuid       varchar(32)
, date       datetime
, event_name varchar(64)
, payload    longvarchar
);

insert into eventstore values (1, '5ed4b1ab61891eba3b7a6743eeda87aa', current_timestamp, 'OrderCreated', 'Some json payload?');


select * from eventstore;