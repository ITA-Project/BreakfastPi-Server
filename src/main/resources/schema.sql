create table user
(
    id          int auto_increment
        primary key,
    username    varchar(255) not null,
    password    varchar(255) null,
    openId      varchar(500) null,
    phone       varchar(20)  null,
    address     varchar(500) null,
    department  varchar(255) null,
    role        varchar(255) null,
    create_time timestamp    null,
    update_time timestamp    null
);

create table box
(
    id          int auto_increment
        primary key,
    address     varchar(500) not null,
    number      int          not null,
    status      int          not null,
    create_time timestamp    null,
    update_time timestamp    null
);

create table shop
(
    id          int auto_increment
        primary key,
    name        varchar(255) not null,
    description varchar(500) null,
    address     varchar(500) not null,
    phone       varchar(20)  not null,
    create_time timestamp    null,
    update_time timestamp    null
);

create table category
(
    id          int auto_increment
        primary key,
    name        varchar(200)  not null,
    status      int default 1 not null,
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
    name        varchar(255) not null,
    description varchar(500) not null,
    image_url   varchar(500) null,
    price       double       null,
    stock       int          null,
    sales       int          null,
    status      int          null,
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
    order_number   varchar(255) not null,
    payment_time   timestamp    null,
    deliver_time   timestamp    null,
    store_time     timestamp    null,
    completed_time timestamp    null,
    cancel_time    timestamp    null,
    amount         double       null,
    user_id        int          null,
    box_id         int          null,
    create_time    timestamp    null,
    update_time    timestamp    null,
    estimated_time timestamp    null,
    status         int          null,
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
    order_id    int       null,
    quantity    int       null,
    create_time timestamp null,
    update_time timestamp null,
    constraint order_product_order_id_fk
        foreign key (order_id) references `order` (id),
    constraint order_product_product_id_fk
        foreign key (product_id) references product (id)
);
