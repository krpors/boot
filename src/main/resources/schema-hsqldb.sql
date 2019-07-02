drop table customer if exists;

create table customer (
      event_id    integer      identity primary key
    , uuid        varchar(36)  not null -- unique id of an aggregate root
    , timestamp   datetime     not null -- timestamp of event
    , event_name  varchar(64)  not null -- CustomerCreated, OrderAdded, PaymentMade
    , class_id    varchar(128) not null -- fully qualified Java class name for serialization
    , payload     longvarchar  not null -- Json/Xml serialized payload
);
