
    create table app_role (
       role_id bigint not null,
        role_name varchar(30) not null,
        primary key (role_id)
    ) engine=InnoDB

    create table hibernate_sequence (
       next_val bigint
    ) engine=InnoDB

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    create table orders (
       id bigint not null,
        date date,
        num varchar(255),
        open bit not null,
        user_user_id bigint,
        primary key (id)
    ) engine=InnoDB

    create table orders_products (
       order_id bigint not null,
        products_id bigint not null
    ) engine=InnoDB

    create table product (
       id bigint not null,
        description varchar(255),
        name varchar(255),
        picture varchar(255),
        price double precision not null,
        stock integer not null,
        primary key (id)
    ) engine=InnoDB

    create table user_account (
       user_id bigint not null,
        adress varchar(255),
        email varchar(36) not null,
        enabled bit not null,
        encryted_password varchar(128) not null,
        _first_name varchar(36),
        last_name varchar(36),
        user_name varchar(36) not null,
        primary key (user_id)
    ) engine=InnoDB

    create table user_role (
       id bigint not null,
        role_id bigint not null,
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB

    alter table app_role 
       add constraint APP_ROLE_UK unique (role_name)

    alter table user_account 
       add constraint USER_ACCOUNT_UK unique (user_name)

    alter table user_role 
       add constraint USER_ROLE_UK unique (user_id, role_id)

    alter table orders 
       add constraint FK65ljuyxxtu96ox671lomh1d5l 
       foreign key (user_user_id) 
       references user_account (user_id)

    alter table orders_products 
       add constraint FKrm329y1qei2vbtf3we4oh1gyx 
       foreign key (products_id) 
       references product (id)

    alter table orders_products 
       add constraint FKe4y1sseio787e4o5hrml7omt5 
       foreign key (order_id) 
       references orders (id)

    alter table user_role 
       add constraint FKp6m37g6n6c288s096400uw8fw 
       foreign key (role_id) 
       references app_role (role_id)

    alter table user_role 
       add constraint FK7ojmv1m1vrxfl3kvt5bi5ur73 
       foreign key (user_id) 
       references user_account (user_id)
