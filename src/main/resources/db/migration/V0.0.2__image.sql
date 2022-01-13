create table image
(
    id serial not null
        constraint image_pk
            primary key,
    name varchar(255),
    image bytea not null
);