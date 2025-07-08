CREATE DATABASE IF NOT EXISTS drinks_business;
USE drinks_business;

CREATE TABLE branches (
    branch_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    location VARCHAR(50) NOT NULL
);

INSERT INTO branches (name, location) VALUES
('Headquarters', 'Nairobi'),
('Branch', 'Nakuru'),
('Branch', 'Mombasa'),
('Branch', 'Kisumu');

CREATE TABLE drinks (
    drink_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

INSERT INTO drinks (name, brand, price) VALUES
('Cola', 'CocaCola', 50.00),
('Orange Juice', 'Delmonte', 70.00),
('Water', 'Dasani', 30.00),
('Malt', 'Tusker', 120.00);

CREATE TABLE inventory (
    inventory_id INT PRIMARY KEY AUTO_INCREMENT,
    branch_id INT NOT NULL,
    drink_id INT NOT NULL,
    quantity INT NOT NULL,
    min_threshold INT NOT NULL DEFAULT 10,
    FOREIGN KEY (branch_id) REFERENCES branches(branch_id),
    FOREIGN KEY (drink_id) REFERENCES drinks(drink_id)
);

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    role VARCHAR(20)
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    branch_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (branch_id) REFERENCES branches(branch_id)
);

CREATE TABLE order_details (
    order_detail_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    drink_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (drink_id) REFERENCES drinks(drink_id)
);

CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    method VARCHAR(50),
    transaction_code VARCHAR(100),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

INSERT INTO inventory (branch_id, drink_id, quantity, min_threshold) VALUES
(1, 1, 100, 10), (1, 2, 80, 10), (1, 3, 120, 10), (1, 4, 60, 10),  
(2, 1, 50, 10),  (2, 2, 40, 10), (2, 3, 60, 10), (2, 4, 30, 10), 
(3, 1, 70, 10), (3, 2, 60, 10), (3, 3, 90, 10),  (3, 4, 40, 10), 
(4, 1, 40, 10),  (4, 2, 30, 10),(4, 3, 50, 10),  (4, 4, 20, 10); 

INSERT INTO users (name, contact, email, password, role)
VALUES ('Mary Wamuyu', '0712345678', 'mary@example.com', 'mary123', 'customer');

INSERT INTO users (name, contact, email, password, role)
VALUES ('System Admin', NULL, 'admin@example.com', 'admin123', 'admin');

INSERT INTO orders (user_id, branch_id, total_amount)
VALUES (1, 2, 150.00); 

INSERT INTO order_details (order_id, drink_id, quantity, price)
VALUES (1, 1, 2, 100.00),(1, 3, 1, 50.00); 

INSERT INTO payments (order_id, method, transaction_code)
VALUES (1, 'M-Pesa', 'MPESA9876AB');
