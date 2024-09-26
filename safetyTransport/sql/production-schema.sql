drop database if exists my_capstone;	
create database my_capstone;

use my_capstone;

create table customers(
	customer_id int auto_increment primary key,
	first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(100) unique not null,
    phone_number varchar(20) not null,
    dob date not null,
    gender varchar(10) not null
);

create table drivers(
	driver_id int auto_increment primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(100) unique not null,
    phone_number varchar(20) not null,
    license_number varchar(50) unique not null,
    car_model varchar(50),
    number_plate varchar(20) not null,
    dob date not null,
    gender varchar(10) not null,
    residential_address varchar(75) not null,
    years_of_experience varchar(20) not null,
    license_expiry_date date not null
    
);

create table appointments(
	appointment_id int auto_increment primary key,
    customer_id int,
    driver_id int,
    approved bit default 0,
    pickup_location varchar(150) not null,
    dropoff_location varchar(150) not null,
    appointment_date date not null,
    end_time time not null,
    start_time time not null,
    foreign key (customer_id) references customers(customer_id),
    foreign key (driver_id) references drivers(driver_id)
);

create table reviews(
	review_id int auto_increment primary key,
    appointment_id int,
    customer_id int,
    driver_id int,
    rating int check(rating >=1 and rating <=5),
    review_text text,
    foreign key (appointment_id) references appointments(appointment_id),
    foreign key (customer_id) references customers(customer_id),
    foreign key (driver_id) references drivers(driver_id)
);
    
    
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
    



