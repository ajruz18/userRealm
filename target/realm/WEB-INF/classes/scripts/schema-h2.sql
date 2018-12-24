drop all objects;

create table userRealm (
    id integer not null,
    description varchar(255),
    key varchar(32),
    name varchar(255) not null,
    primary key (id)
);

alter table userRealm 
    add constraint UK_name unique (name);