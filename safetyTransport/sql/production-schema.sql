drop database if exists my_capstone;
create database my_capstone;

use my_capstone;

create table customers(
	customer_id int auto_increment primary key,
	first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(100) unique not null,
    phone_number varchar(20)
);

create table drivers(
	driver_id int auto_increment primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(100) unique not null,
    phone_number varchar(20),
    license_number varchar(50) unique not null,
    car_model varchar(50),
    number_plate varchar(20)
);

create table rides(
	ride_id int auto_increment primary key,
    customer_id int,
    driver_id int,
    pickup_location varchar(150) not null,
    dropoff_location varchar(150) not null,
    foreign key (customer_id) references customers(customer_id),
    foreign key (driver_id) references drivers(driver_id)
);

create table reviews(
	review_id int auto_increment primary key,
    ride_id int,
    customer_id int,
    driver_id int,
    rating int check(rating >=1 and rating <=5),
    review_text text,
    foreign key (ride_id) references rides(ride_id),
    foreign key (customer_id) references rides(customer_id),
    foreign key (driver_id) references drivers(driver_id)
);
    
    



