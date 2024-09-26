drop database if exists my_capstone_test;
create database my_capstone_test;

use my_capstone_test;

create table customers(
	customer_id int auto_increment primary key,
	first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(100) unique not null,
    phone_number varchar(20),
    dob date not null,
    gender varchar(10) not null
);

create table drivers(
	driver_id int auto_increment primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(100) unique not null,
    phone_number varchar(20),
    license_number varchar(50) unique not null,
    car_model varchar(50),
    number_plate varchar(20),
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

delimiter //

create procedure set_known_good_state()
begin

    set foreign_key_checks = 0;
    truncate table reviews;
    truncate table appointments;
    truncate table drivers;
    truncate table customers;

    set foreign_key_checks = 1;

    insert into customers (first_name, last_name, email, phone_number, dob, gender) values
    ('Muhammed', 'Dibba', 'muhammed10@gmail.com', '+220-777-7777','2000-10-31', 'male'),
    ('Omar', 'Dibba', 'omar11@gmail.com', '+220-888-8888','2000-10-31', 'male'),
    ('Mariama', 'Mbenga', 'mbengam55@gmail.com', '+220-999-9999','2000-10-31', 'male');

    insert into drivers (first_name, last_name, email, phone_number, license_number, car_model, number_plate,
    dob , gender, residential_address, years_of_experience, license_expiry_date) values
    ('Alpha', 'Jallow', 'alphaj@gmail.com', '+220-111-1111', 'GAM-1234', 'Toyota Corolla', 'GAM-1234','2000-10-31', 'male', 'brikama', '20', '2000-10-31'),
    ('Ebrima', 'Marreh', 'marreh1@gmail.com', '+220-222-2222', 'GAM-5678', 'Toyota Camry', 'GAM-5678','2000-10-31', 'male', 'brikama', '20', '2000-10-31'),
    ('Pateh', 'Gajaga', 'pgajaga99@gmail.com', '+220-333-3333', 'GAM-9876', 'Toyota Rav4', 'GAM-9876','2000-10-31', 'male', 'brikama', '20', '2000-10-31');

    insert into appointments (customer_id, driver_id, pickup_location, dropoff_location, appointment_date, start_time, end_time) values
    (1, 1, 'Bakau', 'Serrekunda', '2026-10-31', '10:00:00', '12:00:00'),
    (2, 2, 'Bijilo', 'Kololi', '2026-10-31', '10:00:00', '12:00:00'),
    (3, 3, 'Bakoteh', 'Bakau', '2026-10-31', '10:00:00', '12:00:00');

    insert into reviews (appointment_id, customer_id, driver_id, rating, review_text) values
    (1, 1, 1, 5, 'Great service'),
    (2, 2, 2, 4, 'Good service'),
    (3, 3, 3, 3, 'Average service');

end //

delimiter //
