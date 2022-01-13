create table product_category
(
    name varchar(255)
        constraint product_category_pk
            primary key
);

create table product
(
    id serial not null
        constraint product_pk
            primary key,
    name varchar(255) not null,
    icon_id int
        constraint product_image_id_fk
            references image,
    category varchar(255)
        constraint product_product_category_name_fk
            references product_category
);

create unique index product_name_uindex
    on product (name);