create table shelf
(
    id serial not null
        constraint shelf_pk
            primary key,
    name varchar(255),
    owner_email varchar(255)
        constraint shelf_my_user_email_fk
            references my_user,
    is_shopping_list boolean default false,
    is_null_shelf boolean default false
);

create table category
(
    id serial not null
        constraint category_pk
            primary key,
    name varchar(255),
    shelf_id int
        constraint category_shelf_id_fk
            references shelf
);

create table category_product
(
    category_id int
        constraint category_product_category_id_fk
            references category,
    product_id int
        constraint category_product_product_id_fk
            references product,
    amount int,
    constraint category_product_pk
        primary key (category_id, product_id)
);
