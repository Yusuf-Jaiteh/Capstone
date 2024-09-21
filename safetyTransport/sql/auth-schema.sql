drop database if exists my_capstone_auth;
create database my_capstone_auth;

use my_capstone_auth;

create table app_user (
	app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled boolean not null default(true),
	`locked` boolean not null default(false),
    authority varchar(50) not null
);

insert into app_user (username, password_hash, authority) values
	('user', '$2a$10$8DlmRK86p1.SROHo3DbNDegxZJ03vsrvP9xfqOCPsDcu6Wl5zrKIW', 'USER'),
    ('admin', '$2a$10$p/EhpWSu2VJC/MxrDwI9AOQN5qujvV9h5ypEU.NJTzUT4Bc9689t.', 'ADMIN'),
    ('public', '$2a$10$9qPwgPqbFG9dhk2PNA0ZK.wGHM36rDi5O6l/Z5lncIzDHvqB4EOCG', '');