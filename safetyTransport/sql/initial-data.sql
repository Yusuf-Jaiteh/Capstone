use my_capstone;

INSERT INTO customers (first_name, last_name, email, phone_number) VALUES
('Lamin', 'Darboe', 'laminarboe@gmail.com', '+220 777 888 999'),
('Awa', 'Sanneh', 'awasanneh@yahoo.com', '+220 666 777 888'),
('Basiru', 'Jallow', 'basirujallow@hotmail.com', '+220 555 666 777'),
('Fatou', 'Touray', 'fatoutouray@outlook.com', '+220 444 555 666'),
('Musa', 'Saidy', 'musasaidy@gmail.com', '+220 333 444 555');

INSERT INTO drivers (first_name, last_name, email, phone_number, license_number, car_model, number_plate) VALUES
('Omar', 'Ceesay', 'omarceesay@gmail.com', '+220 111 222 333', 'G123456', 'Toyota Corolla', 'BJ 1234'),
('Kaddy', 'Cham', 'kaddycham@yahoo.com', '+220 222 333 444', 'G234567', 'Kia Rio', 'BK 2345'),
('Mbye', 'Njie', 'mbyenjie@hotmail.com', '+220 333 444 555', 'G345678', 'Hyundai Sonata', 'BL 3456'),
('Yankuba', 'Colley', 'yankubacrolley@outlook.com', '+220 444 555 666', 'G456789', 'Nissan Almera', 'BM 4567'),
('Amina', 'Jawara', 'aminajawara@gmail.com', '+220 555 666 777', 'G567890', 'Ford Fiesta', 'BN 5678');

INSERT INTO appointment (customer_id, driver_id, pickup_location, dropoff_location) VALUES
(1, 1, 'Banjul', 'Kotu'),
(2, 2, 'Kotu', 'Brufut'),
(3, 3, 'Brufut', 'Tanji'),
(4, 4, 'Tanji', 'Gunjur'),
(5, 5, 'Gunjur', 'Senegambia');

INSERT INTO reviews (appointment_id, customer_id, driver_id, rating, review_text) VALUES
(1, 1, 1, 5, 'Great ride, friendly driver.'),
(2, 2, 2, 4, 'Good service, but a bit late.'),
(3, 3, 3, 3, 'Average ride, could be cleaner.'),
(4, 4, 4, 5, 'Excellent driver, very helpful.'),
(5, 5, 5, 4, 'Nice ride, but could be more comfortable.');