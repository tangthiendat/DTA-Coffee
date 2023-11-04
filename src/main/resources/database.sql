create schema dtacoffee;
use dtacoffee;

create table product_type
(
    prodtype_id   char(4)     not null
        primary key,
    prodtype_name varchar(50) null
);

create table product
(
    `product_id` char(4)
        primary key,
    `product_name` varchar(100) null,
    `prodtype_id`   char(4) null,
    `unit_price`       int       null,
    `status`      varchar(20) null,
    constraint FK_prodtypeID
        foreign key (prodtype_id) references product_type(prodtype_id)
);

create table `order`
(
    order_id     char(10)   not null
        primary key,
    created_date timestamp  null,
    table_number varchar(3) null,
    total_value  bigint     null,
    paid         tinyint(1) null
);

create table order_details
(
    order_id    char(10) not null,
    product_id char(4) not null,
    quantity   int null,
    unit_price int null,
    primary key (order_id, product_id),
    constraint FK_productID
        foreign key (product_id) references product (product_id),
    constraint FK_orderID
        foreign key (order_id) references `order` (order_id)
);