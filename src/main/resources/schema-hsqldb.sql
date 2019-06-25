drop table eventstore if exists;

create table eventstore (
      id         integer
    , uuid       varchar(32)
    , date       datetime
    , event_name varchar(64)
    , payload    longvarchar
);

create sequence seq_eventstore;
