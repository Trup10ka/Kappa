CREATE DATABASE IF NOT EXISTS kappa;

USE kappa;

CREATE TABLE customer (
                          id INT NOT NULL AUTO_INCREMENT,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          sex ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL,
                          customer_credits INT NOT NULL CHECK (customer_credits >= 0),
                          PRIMARY KEY (id)
);

CREATE TABLE product_category (
                                  id INT NOT NULL AUTO_INCREMENT,
                                  category_name VARCHAR(80) NOT NULL,
                                  PRIMARY KEY (id)
);

CREATE TABLE product (
                         id INT NOT NULL AUTO_INCREMENT,
                         product_category_id INT NOT NULL,
                         name VARCHAR(80) NOT NULL,
                         details TEXT NOT NULL,
                         is_available BOOLEAN NOT NULL,
                         price FLOAT NOT NULL CHECK (price >= 0),
                         PRIMARY KEY (id)
);

CREATE TABLE `order` (
                         id INT NOT NULL AUTO_INCREMENT,
                         customer_id INT NOT NULL,
                         place_date DATE NOT NULL,
                         price FLOAT NOT NULL CHECK (price >= 0),
                         delivery_address VARCHAR(400) NOT NULL,
                         delivery_zip CHAR(5) NOT NULL, -- Assuming a fixed zip code length of 5 characters
                         expected_delivery DATETIME NOT NULL,
                         order_note TEXT,
                         PRIMARY KEY (id)
);

CREATE TABLE product_to_order (
                                  id INT NOT NULL AUTO_INCREMENT,
                                  product_id INT NOT NULL,
                                  order_id INT NOT NULL,
                                  number_of_items INT NOT NULL CHECK (number_of_items >= 0),
                                  total_price FLOAT NOT NULL CHECK (total_price >= 0),
                                  PRIMARY KEY (id)
);

ALTER TABLE `order`
    ADD CONSTRAINT order_customer_FK FOREIGN KEY (customer_id)
        REFERENCES customer (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION;

ALTER TABLE product
    ADD CONSTRAINT product_product_category_FK FOREIGN KEY (product_category_id)
        REFERENCES product_category (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION;

ALTER TABLE product_to_order
    ADD CONSTRAINT product_to_order_order_FK FOREIGN KEY (order_id)
        REFERENCES `order` (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION;

ALTER TABLE product_to_order
    ADD CONSTRAINT product_to_order_product_FK FOREIGN KEY (product_id)
        REFERENCES product (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION;


CREATE VIEW most_ordered_products AS
SELECT p.name AS product_name, SUM(pto.number_of_items) AS total_ordered
FROM product_to_order pto
JOIN product p ON pto.product_id = p.id
GROUP BY p.name
    ORDER BY total_ordered DESC;
