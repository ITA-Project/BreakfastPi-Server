create table user
(
    id          int auto_increment
        primary key,
    username    varchar(20) not null,
    password    varchar(25) null,
    openId      varchar(50) null,
    phone       char(11)  null,
    address     varchar(50) null,
    department  varchar(20) null,
    role        varchar(20) null,
    create_time timestamp    null,
    update_time timestamp    null
);

create table box
(
    id          int auto_increment
        primary key,
    address     varchar(20) not null,
    number      int          not null,
    status      tinyint      not null,
    create_time timestamp    null,
    update_time timestamp    null
);

create table shop
(
    id          int auto_increment
        primary key,
    name        varchar(20) not null,
    description varchar(500) null,
    address     varchar(20) not null,
    phone       char(11)  not null,
    create_time timestamp    null,
    update_time timestamp    null
);

create table category
(
    id          int auto_increment
        primary key,
    name        varchar(20)  not null,
    status      tinyint default 1 not null,
    sequence    int           null,
    shop_id     int           null,
    create_time timestamp     null,
    update_time timestamp     null,
    constraint category_shop_id_fk
        foreign key (shop_id) references shop (id)
);

create table product
(
    id          int auto_increment
        primary key,
    name        varchar(50) not null,
    description varchar(500) not null,
    image_url   varchar(50) null,
    price       decimal(6,2)     null,
    stock       int          null,
    sales       int          null,
    status      tinyint      null,
    category_id int          null,
    create_time timestamp    null,
    update_time timestamp    null,
    constraint Product_category_id_fk
        foreign key (category_id) references category (id)
);

create table cart
(
    id          int auto_increment
        primary key,
    quantity    int       null,
    product_id  int       null,
    user_id     int       null,
    shop_id     int       null,
    create_time timestamp null,
    update_time timestamp null,
    constraint cart_product_id_fk
        foreign key (product_id) references product (id),
    constraint cart_shop_id_fk
        foreign key (shop_id) references shop (id),
    constraint cart_user_id_fk
        foreign key (user_id) references user (id)
);

create table `order`
(
    id             int auto_increment
        primary key,
    order_number   char(19) not null,
    payment_time   timestamp    null,
    deliver_time   timestamp    null,
    store_time     timestamp    null,
    accepted_time  timestamp    null,
    completed_time timestamp    null,
    cancel_time    timestamp    null,
    amount         decimal(6,2)   null,
    user_id        int          null,
    box_id         int          null,
    create_time    timestamp    null,
    update_time    timestamp    null,
    estimated_time timestamp    null,
    status         tinyint      null,
    constraint order_box_id_fk
        foreign key (box_id) references box (id),
    constraint order_user_id_fk
        foreign key (user_id) references user (id)
);

create table order_item
(
    id          int auto_increment
        primary key,
    product_id  int       null,
    order_number    char(19)   not null,
    quantity    int       null,
    create_time timestamp null,
    update_time timestamp null,
    constraint order_product_product_id_fk
        foreign key (product_id) references product (id)
);
