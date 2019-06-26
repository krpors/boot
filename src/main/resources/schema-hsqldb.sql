drop table customer if exists;

create table customer (
      event_id    integer     -- incremented by sequence
    , uuid        varchar(36) -- unique id of an aggregate root
    , timestamp   datetime    -- timestamp of event
    , event_name  varchar(64) -- CustomerCreated, OrderAdded, PaymentMade
    , payload     longvarchar -- Json/Xml serialized payload
);

create sequence seq_customer_event_id;
