create table product
(
    id serial not null
        constraint product_pk
            primary key,
    name varchar(255) not null
);

create unique index product_name_uindex
    on product (name);