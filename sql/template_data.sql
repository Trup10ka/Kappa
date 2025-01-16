-- Customers

INSERT INTO customer (first_name, last_name, sex, customer_credits)
VALUES ('John', 'Doe', 'MALE', 1200),
       ('Jane', 'Smith', 'FEMALE', 1500),
       ('Alex', 'Taylor', 'OTHER', 500),
       ('Emily', 'Johnson', 'FEMALE', 2300),
       ('Michael', 'Brown', 'MALE', 1800),
       ('Chris', 'Davis', 'MALE', 750),
       ('Sarah', 'Miller', 'FEMALE', 420),
       ('Jordan', 'Garcia', 'OTHER', 1300),
       ('Sophia', 'Martinez', 'FEMALE', 2100),
       ('David', 'Hernandez', 'MALE', 50),
       ('Ella', 'Lopez', 'FEMALE', 1700),
       ('Ethan', 'Wilson', 'MALE', 650),
       ('Mia', 'Anderson', 'FEMALE', 900),
       ('Ryan', 'Thomas', 'MALE', 400),
       ('Avery', 'Moore', 'OTHER', 120),
       ('Isabella', 'Jackson', 'FEMALE', 2500),
       ('Benjamin', 'Lee', 'MALE', 300),
       ('Oliver', 'White', 'MALE', 1150),
       ('Liam', 'Harris', 'MALE', 680),
       ('Charlotte', 'Clark', 'FEMALE', 240),
       ('James', 'Walker', 'MALE', 890),
       ('Amelia', 'Young', 'FEMALE', 2000),
       ('Lucas', 'King', 'MALE', 350),
       ('Harper', 'Hall', 'FEMALE', 180),
       ('Mason', 'Allen', 'MALE', 970);

-- Products categories
INSERT INTO product_category (category_name)
VALUES ('Electronics'),
       ('Clothing'),
       ('Home Appliances'),
       ('Books'),
       ('Toys'),
       ('Sports Equipment'),
       ('Groceries');

-- Products
INSERT INTO product (product_category_id, name, details, is_available, price)
VALUES (1, 'Smartphone', 'High-performance smartphone with 128GB storage', TRUE, 699.99),
       (1, 'Laptop', '15-inch laptop with 16GB RAM and 512GB SSD', TRUE, 1199.99),
       (1, 'Wireless Earbuds', 'Bluetooth 5.0 earbuds with noise cancellation', TRUE, 149.99),
       (1, '4K Monitor', '27-inch 4K UHD monitor with HDR support', TRUE, 349.99),
       (1, 'Gaming Console', 'Next-gen gaming console with 1TB storage', FALSE, 499.99),

       (2, 'Men T-Shirt', 'Cotton t-shirt available in various colors', TRUE, 19.99),
       (2, 'Women Jeans', 'Skinny fit jeans with stretch fabric', TRUE, 49.99),
       (2, 'Winter Jacket', 'Waterproof winter jacket with a hood', TRUE, 89.99),
       (2, 'Sneakers', 'Comfortable sneakers for everyday wear', TRUE, 59.99),
       (2, 'Baseball Cap', 'Adjustable cap with embroidered logo', TRUE, 14.99),

       (3, 'Refrigerator', 'Energy-efficient refrigerator with 300L capacity', TRUE, 599.99),
       (3, 'Washing Machine', 'Front-loading washing machine with 8kg capacity', TRUE, 499.99),
       (3, 'Microwave Oven', '800W microwave oven with multiple presets', TRUE, 99.99),
       (3, 'Air Conditioner', 'Split AC with inverter technology', TRUE, 799.99),
       (3, 'Vacuum Cleaner', 'Cordless vacuum cleaner with HEPA filter', TRUE, 149.99),

       (4, 'Fiction Novel', 'Bestselling novel with captivating story', TRUE, 12.99),
       (4, 'Cookbook', 'Recipes from around the world', TRUE, 24.99),
       (4, 'Self-Help Book', 'Motivational guide to personal growth', TRUE, 14.99),
       (4, 'Children Books', 'Illustrated book for kids aged 5-8', TRUE, 9.99),
       (4, 'Graphic Novel', 'Popular graphic novel series volume 1', TRUE, 19.99),

       (5, 'Action Figure', 'Articulated action figure with accessories', TRUE, 29.99),
       (5, 'Board Game', 'Strategy board game for 2-4 players', TRUE, 39.99),
       (5, 'Dollhouse', 'Wooden dollhouse with furniture set', TRUE, 89.99),
       (5, 'Puzzle Set', '1000-piece puzzle featuring a scenic view', TRUE, 14.99),
       (5, 'Remote Control Car', 'Rechargeable RC car with high speed', TRUE, 49.99);

-- Orders

INSERT INTO `order` (customer_id, place_date, price, delivery_address, delivery_zip, expected_delivery, order_note)
VALUES (1, '2025-01-10', 349.99, '123 Main Street, Springfield', '12345', '2025-01-14 14:00:00',
        'Leave at the front door'),
       (2, '2025-01-09', 699.99, '456 Oak Avenue, Maple Town', '23456', '2025-01-13 12:00:00', 'Handle with care'),
       (3, '2025-01-08', 59.99, '789 Pine Road, Elm City', '34567', '2025-01-12 16:00:00',
        'Ring the doorbell upon arrival'),
       (4, '2025-01-07', 199.99, '101 Birch Blvd, Cedarville', '45678', '2025-01-11 10:00:00', NULL),
       (5, '2025-01-06', 49.99, '202 Willow Lane, Poplar Creek', '56789', '2025-01-10 18:00:00',
        'Call before delivery'),
       (6, '2025-01-05', 129.99, '303 Aspen Drive, Redwood City', '67890', '2025-01-09 11:00:00',
        'Deliver to the side door'),
       (7, '2025-01-04', 89.99, '404 Chestnut Court, Spruce Valley', '78901', '2025-01-08 15:00:00', NULL),
       (8, '2025-01-03', 249.99, '505 Walnut Street, Fir Town', '89012', '2025-01-07 13:00:00',
        'Include a gift receipt'),
       (9, '2025-01-02', 39.99, '606 Elm Avenue, Pinewood', '90123', '2025-01-06 17:00:00', NULL),
       (10, '2025-01-01', 599.99, '707 Maple Boulevard, Cypress City', '01234', '2025-01-05 09:00:00',
        'Rush delivery, please!');

-- Products to orders
INSERT INTO product_to_order (product_id, order_id, number_of_items, total_price)
VALUES (1, 1, 1, 699.99),   -- 1 Smartphone for Order 1
       (2, 1, 1, 1199.99),  -- 1 Laptop for Order 1
       (3, 2, 2, 299.98),   -- 2 Wireless Earbuds for Order 2
       (7, 3, 3, 59.97),    -- 3 Men's T-Shirts for Order 3
       (14, 4, 1, 199.99),  -- 1 Air Conditioner for Order 4
       (11, 5, 1, 49.99),   -- 1 Refrigerator for Order 5
       (17, 6, 5, 74.95),   -- 5 Self-Help Books for Order 6
       (21, 7, 1, 29.99); -- 1 Washing Machine for Order 10
