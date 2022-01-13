create table my_user
(
    email varchar(255)
        constraint my_user_pk
            primary key,
    password varchar(255),
    phone varchar(255),
    firstname varchar(255),
    surname varchar(255),
    enabled bool default true
);

create table my_user_authority
(
    email varchar(255) not null
        constraint my_user_authority_my_user_email_fk
            references my_user,
    authority varchar(255) not null
);

create unique index my_user_authority_email_authority_uindex
    on my_user_authority (email, authority);