use my_capstone;

INSERT INTO customers (first_name, last_name, email, phone_number, dob , gender) VALUES
('Lamin', 'Darboe', 'laminarboe@gmail.com', '+220 777 888 999','2000-10-31', 'male'),
('Awa', 'Sanneh', 'awasanneh@yahoo.com', '+220 666 777 888', '2000-10-31', 'female'),
('Basiru', 'Jallow', 'basirujallow@hotmail.com', '+220 555 666 777','2000-10-31', 'male'),
('Fatou', 'Touray', 'fatoutouray@outlook.com', '+220 444 555 666','2000-10-31', 'female'),
('Musa', 'Saidy', 'musasaidy@gmail.com', '+220 333 444 555', '2000-10-31', 'male');

INSERT INTO drivers (first_name, last_name, email, phone_number, license_number, car_model, number_plate,
dob , gender, residential_address, years_of_experience, license_expiry_date) VALUES
('Omar', 'Ceesay', 'omarceesay@gmail.com', '+220 111 222 333', 'G123456', 'Toyota Corolla', 'BJ 1234','2000-10-31', 'male', 'brikama', '20', '2000-10-31'),
('Kaddy', 'Cham', 'kaddycham@yahoo.com', '+220 222 333 444', 'G234567', 'Kia Rio', 'BK 2345','2000-10-31', 'male', 'brikama', '20', '2000-10-31'),
('Mbye', 'Njie', 'mbyenjie@hotmail.com', '+220 333 444 555', 'G345678', 'Hyundai Sonata', 'BL 3456','2000-10-31', 'male', 'brikama', '20', '2000-10-31'),
('Yankuba', 'Colley', 'yankubacrolley@outlook.com', '+220 444 555 666', 'G456789', 'Nissan Almera', 'BM 4567','2000-10-31', 'male', 'brikama', '20', '2000-10-31'),
('Amina', 'Jawara', 'aminajawara@gmail.com', '+220 555 666 777', 'G567890', 'Ford Fiesta', 'BN 5678','2000-10-31', 'male', 'brikama', '20', '2000-10-31');

INSERT INTO appointments (customer_id, driver_id, pickup_location, dropoff_location, appointment_date, start_time, end_time) VALUES
(1, 1, 'Banjul', 'Kotu', '2026-10-31', '10:00:00', '11:00:00'),
(2, 2, 'Kotu', 'Brufut', '2026-10-31', '10:00:00', '11:00:00'),
(3, 3, 'Brufut', 'Tanji', '2026-10-31', '10:00:00', '11:00:00'),
(4, 4, 'Tanji', 'Gunjur', '2026-10-31', '10:00:00', '11:00:00'),
(5, 5, 'Gunjur', 'Senegambia', '2026-10-31', '10:00:00', '11:00:00');

INSERT INTO reviews (appointment_id, customer_id, driver_id, rating, review_text) VALUES
(1, 1, 1, 5, 'Great ride, friendly driver.'),
(2, 2, 2, 4, 'Good service, but a bit late.'),
(3, 3, 3, 3, 'Average ride, could be cleaner.'),
(4, 4, 4, 5, 'Excellent driver, very helpful.'),
(5, 5, 5, 4, 'Nice ride, but could be more comfortable.');