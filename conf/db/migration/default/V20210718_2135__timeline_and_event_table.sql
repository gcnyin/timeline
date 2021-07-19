create table "timeline"
(
    "id"           serial primary key not null,
    "title"        varchar(256)       not null,
    "create_date" timestamp default current_timestamp
);

create table "timeline_item"
(
    "id"           serial primary key not null,
    "timeline_id"  integer references "timeline" ("id"),
    "title"        varchar(256)       not null,
    "record_time"  timestamp default current_timestamp,
    "description"  varchar(1024)      not null,
    "create_date" timestamp default current_timestamp
);
