create schema dtacoffee;
use dtacoffee;

create table product
(
    `product_id` int auto_increment
        primary key,
    `product_name` varchar(100) null,
    `type`        varchar(100) null,
    `price`       int       null,
    `status`      varchar(100) null
);

create table `order`
(
    order_id     int auto_increment
        primary key,
    order_number varchar(12) null,
    create_date timestamp   null,
    total_value  bigint       null,
    constraint order_pk
        unique (order_number)
);

create table order_details
(
    order_id    int not null,
    product_id int not null,
    quantity   int null,
    price int null,
    primary key (order_id, product_id),
    constraint FK_productID
        foreign key (product_id) references product (product_id),
    constraint FK_orderID
        foreign key (order_id) references `order` (order_id)
);