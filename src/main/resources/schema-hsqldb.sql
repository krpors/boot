drop table customer if exists;

create table customer (
      event_id    integer     -- incremented by sequence
    , customer_id integer     -- customer id remains the same over multiple events belonging to that customer
    , date        datetime    -- timestamp of event
    , event_name  varchar(64) -- CustomerCreated, OrderAdded, PaymentMade
    , payload     longvarchar -- Json/Xml serialized payload
);

create sequence seq_customer_event_id;
create sequence seq_customer_customer_id;
