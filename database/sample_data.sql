-- ==========================================
-- SUPPLIERS (10)
-- ==========================================

INSERT INTO Supplier
(supplier_name, contact_number, email_id, supplier_address)
VALUES
('Bata Distributors','9876500001','bata@gmail.com','Delhi'),
('Nike India','9876500002','nike@gmail.com','Mumbai'),
('Adidas India','9876500003','adidas@gmail.com','Bangalore'),
('Puma India','9876500004','puma@gmail.com','Hyderabad'),
('Woodland Suppliers','9876500005','woodland@gmail.com','Noida'),
('Campus Shoes','9876500006','campus@gmail.com','Agra'),
('Skechers India','9876500007','skechers@gmail.com','Pune'),
('Red Chief','9876500008','redchief@gmail.com','Kanpur'),
('Liberty Shoes','9876500009','liberty@gmail.com','Karnal'),
('Paragon Footwear','9876500010','paragon@gmail.com','Kochi');

-- ==========================================
-- CUSTOMERS (20)
-- ==========================================

INSERT INTO Customer
(customer_name, customer_contact_number)
VALUES
('Rahul Sharma','9999000001'),
('Priya Singh','9999000002'),
('Amit Verma','9999000003'),
('Sneha Kapoor','9999000004'),
('Rohit Gupta','9999000005'),
('Anjali Mehta','9999000006'),
('Vikas Yadav','9999000007'),
('Neha Agarwal','9999000008'),
('Karan Malhotra','9999000009'),
('Simran Kaur','9999000010'),
('Arjun Patel','9999000011'),
('Riya Jain','9999000012'),
('Harsh Gupta','9999000013'),
('Megha Arora','9999000014'),
('Nikhil Sharma','9999000015'),
('Pooja Saini','9999000016'),
('Sahil Khanna','9999000017'),
('Tanvi Kapoor','9999000018'),
('Manish Bansal','9999000019'),
('Aisha Khan','9999000020');

-- ==========================================
-- PRODUCTS (15)
-- ==========================================

INSERT INTO Product
(
    product_name,
    product_brand,
    product_category,
    gender,
    created_at,
    updated_at,
    cost_price,
    selling_price
)
VALUES
('Air Max','Nike','Sports','M',CURRENT_DATE,CURRENT_DATE,2500.00,3800.00),
('Ultraboost','Adidas','Sports','M',CURRENT_DATE,CURRENT_DATE,3100.00,4500.00),
('Classic Leather','Bata','Casual','M',CURRENT_DATE,CURRENT_DATE,1200.00,1900.00),
('Ignite Run','Puma','Sports','F',CURRENT_DATE,CURRENT_DATE,2200.00,3400.00),
('Woodland Trek','Woodland','Boots','M',CURRENT_DATE,CURRENT_DATE,2800.00,4300.00),
('Campus Runner','Campus','Sports','M',CURRENT_DATE,CURRENT_DATE,1400.00,2200.00),
('Go Walk','Skechers','Walking','F',CURRENT_DATE,CURRENT_DATE,2600.00,3900.00),
('Formal Pro','Red Chief','Formal','M',CURRENT_DATE,CURRENT_DATE,1800.00,2900.00),
('Comfort Walk','Liberty','Casual','F',CURRENT_DATE,CURRENT_DATE,1300.00,2100.00),
('Paragon Daily','Paragon','Slippers','M',CURRENT_DATE,CURRENT_DATE,350.00,650.00),
('Nike Revolution','Nike','Sports','F',CURRENT_DATE,CURRENT_DATE,2400.00,3600.00),
('Adidas Lite','Adidas','Sports','F',CURRENT_DATE,CURRENT_DATE,2500.00,3700.00),
('Bata School','Bata','School','M',CURRENT_DATE,CURRENT_DATE,900.00,1450.00),
('Puma Street','Puma','Casual','M',CURRENT_DATE,CURRENT_DATE,2100.00,3300.00),
('Woodland Explorer','Woodland','Boots','F',CURRENT_DATE,CURRENT_DATE,3200.00,4900.00);

-- ==========================================
-- PRODUCT VARIANTS (45)
-- ==========================================

INSERT INTO Product_Variant
(product_id, size_of_product, colour)
VALUES

-- Product 1 : Air Max
(1,7,'Black'),
(1,8,'Black'),
(1,9,'White'),

-- Product 2 : Ultraboost
(2,8,'White'),
(2,9,'Black'),
(2,10,'Blue'),

-- Product 3 : Classic Leather
(3,7,'Brown'),
(3,8,'Black'),
(3,9,'Brown'),

-- Product 4 : Ignite Run
(4,6,'Pink'),
(4,7,'White'),
(4,8,'Grey'),

-- Product 5 : Woodland Trek
(5,8,'Brown'),
(5,9,'Olive'),
(5,10,'Black'),

-- Product 6 : Campus Runner
(6,7,'Blue'),
(6,8,'Black'),
(6,9,'Grey'),

-- Product 7 : Go Walk
(7,6,'White'),
(7,7,'Pink'),
(7,8,'Grey'),

-- Product 8 : Formal Pro
(8,8,'Black'),
(8,9,'Brown'),
(8,10,'Tan'),

-- Product 9 : Comfort Walk
(9,6,'Pink'),
(9,7,'Blue'),
(9,8,'Black'),

-- Product 10 : Paragon Daily
(10,7,'Blue'),
(10,8,'Black'),
(10,9,'Grey'),

-- Product 11 : Nike Revolution
(11,6,'White'),
(11,7,'Black'),
(11,8,'Purple'),

-- Product 12 : Adidas Lite
(12,6,'Grey'),
(12,7,'Blue'),
(12,8,'White'),

-- Product 13 : Bata School
(13,5,'Black'),
(13,6,'Black'),
(13,7,'Black'),

-- Product 14 : Puma Street
(14,8,'Red'),
(14,9,'Black'),
(14,10,'White'),

-- Product 15 : Woodland Explorer
(15,7,'Olive'),
(15,8,'Brown'),
(15,9,'Black');



-- ==========================================
-- INVENTORY
-- ==========================================

INSERT INTO Inventory
(
    variant_id,
    quantity_in_stock,
    created_at,
    updated_at
)
VALUES
(1,25,CURRENT_DATE,CURRENT_DATE),
(2,20,CURRENT_DATE,CURRENT_DATE),
(3,18,CURRENT_DATE,CURRENT_DATE),

(4,22,CURRENT_DATE,CURRENT_DATE),
(5,17,CURRENT_DATE,CURRENT_DATE),
(6,15,CURRENT_DATE,CURRENT_DATE),

(7,30,CURRENT_DATE,CURRENT_DATE),
(8,24,CURRENT_DATE,CURRENT_DATE),
(9,18,CURRENT_DATE,CURRENT_DATE),

(10,16,CURRENT_DATE,CURRENT_DATE),
(11,15,CURRENT_DATE,CURRENT_DATE),
(12,14,CURRENT_DATE,CURRENT_DATE),

(13,12,CURRENT_DATE,CURRENT_DATE),
(14,10,CURRENT_DATE,CURRENT_DATE),
(15,9,CURRENT_DATE,CURRENT_DATE),

(16,20,CURRENT_DATE,CURRENT_DATE),
(17,18,CURRENT_DATE,CURRENT_DATE),
(18,17,CURRENT_DATE,CURRENT_DATE),

(19,14,CURRENT_DATE,CURRENT_DATE),
(20,13,CURRENT_DATE,CURRENT_DATE),
(21,12,CURRENT_DATE,CURRENT_DATE),

(22,16,CURRENT_DATE,CURRENT_DATE),
(23,15,CURRENT_DATE,CURRENT_DATE),
(24,12,CURRENT_DATE,CURRENT_DATE),

(25,20,CURRENT_DATE,CURRENT_DATE),
(26,18,CURRENT_DATE,CURRENT_DATE),
(27,16,CURRENT_DATE,CURRENT_DATE),

(28,40,CURRENT_DATE,CURRENT_DATE),
(29,35,CURRENT_DATE,CURRENT_DATE),
(30,30,CURRENT_DATE,CURRENT_DATE),

(31,18,CURRENT_DATE,CURRENT_DATE),
(32,16,CURRENT_DATE,CURRENT_DATE),
(33,15,CURRENT_DATE,CURRENT_DATE),

(34,20,CURRENT_DATE,CURRENT_DATE),
(35,18,CURRENT_DATE,CURRENT_DATE),
(36,16,CURRENT_DATE,CURRENT_DATE),

(37,30,CURRENT_DATE,CURRENT_DATE),
(38,28,CURRENT_DATE,CURRENT_DATE),
(39,25,CURRENT_DATE,CURRENT_DATE),

(40,15,CURRENT_DATE,CURRENT_DATE),
(41,14,CURRENT_DATE,CURRENT_DATE),
(42,13,CURRENT_DATE,CURRENT_DATE),

(43,10,CURRENT_DATE,CURRENT_DATE),
(44,9,CURRENT_DATE,CURRENT_DATE),
(45,8,CURRENT_DATE,CURRENT_DATE);

-- ==========================================
-- PURCHASES (20)
-- ==========================================

INSERT INTO Purchase
(
    supplier_id,
    purchase_date,
    invoice_number,
    payment_method,
    total_payment_amount
)
VALUES
(1,'2026-01-05','INV1001','UPI',72000.00),
(2,'2026-01-09','INV1002','Bank Transfer',86500.00),
(3,'2026-01-15','INV1003','Cash',41500.00),
(4,'2026-01-22','INV1004','UPI',54000.00),
(5,'2026-02-03','INV1005','Bank Transfer',78500.00),

(6,'2026-02-10','INV1006','Cash',39500.00),
(7,'2026-02-18','INV1007','UPI',62200.00),
(8,'2026-02-25','INV1008','Cash',44800.00),
(9,'2026-03-04','INV1009','Bank Transfer',51400.00),
(10,'2026-03-12','INV1010','UPI',19300.00),

(1,'2026-03-20','INV1011','Cash',53400.00),
(2,'2026-03-28','INV1012','UPI',72600.00),
(3,'2026-04-05','INV1013','Bank Transfer',40600.00),
(4,'2026-04-13','INV1014','Cash',58100.00),
(5,'2026-04-21','INV1015','UPI',91800.00),

(6,'2026-05-03','INV1016','Cash',45200.00),
(7,'2026-05-12','INV1017','Bank Transfer',66400.00),
(8,'2026-05-22','INV1018','UPI',57500.00),
(9,'2026-06-02','INV1019','Cash',43100.00),
(10,'2026-06-15','INV1020','Bank Transfer',20800.00);

-- ==========================================
-- PURCHASE ITEMS
-- ==========================================

INSERT INTO Purchase_Item
(purchase_id, quantity, variant_id, cost_price)
VALUES

-- Purchase 1 (Bata)
(1,20,7,1200),
(1,18,8,1200),
(1,15,9,1200),

-- Purchase 2 (Nike)
(2,12,1,2500),
(2,10,2,2500),
(2,8,3,2500),

-- Purchase 3 (Adidas)
(3,15,4,3100),
(3,12,5,3100),
(3,10,6,3100),

-- Purchase 4 (Puma)
(4,18,10,2200),
(4,15,11,2200),
(4,12,12,2200),

-- Purchase 5 (Woodland)
(5,10,13,2800),
(5,10,14,2800),
(5,8,15,2800),

-- Purchase 6 (Campus)
(6,20,16,1400),
(6,18,17,1400),
(6,15,18,1400),

-- Purchase 7 (Skechers)
(7,10,19,2600),
(7,10,20,2600),
(7,8,21,2600),

-- Purchase 8 (Red Chief)
(8,12,22,1800),
(8,10,23,1800),
(8,8,24,1800),

-- Purchase 9 (Liberty)
(9,15,25,1300),
(9,12,26,1300),
(9,10,27,1300),

-- Purchase 10 (Paragon)
(10,30,28,350),
(10,25,29,350),
(10,20,30,350),

-- Purchase 11 (Nike Revolution)
(11,12,31,2400),
(11,10,32,2400),
(11,8,33,2400),

-- Purchase 12 (Adidas Lite)
(12,12,34,2500),
(12,10,35,2500),
(12,8,36,2500),

-- Purchase 13 (Bata School)
(13,20,37,900),
(13,18,38,900),
(13,15,39,900),

-- Purchase 14 (Puma Street)
(14,12,40,2100),
(14,10,41,2100),
(14,8,42,2100),

-- Purchase 15 (Woodland Explorer)
(15,8,43,3200),
(15,8,44,3200),
(15,6,45,3200),

-- Purchase 16
(16,15,7,1200),
(16,12,8,1200),
(16,10,9,1200),

-- Purchase 17
(17,8,1,2500),
(17,10,2,2500),
(17,8,3,2500),

-- Purchase 18
(18,10,13,2800),
(18,10,14,2800),
(18,8,15,2800),

-- Purchase 19
(19,10,22,1800),
(19,10,23,1800),
(19,8,24,1800),

-- Purchase 20
(20,20,28,350),
(20,18,29,350),
(20,15,30,350);

-- ==========================================
-- SALES (20)
-- ==========================================

INSERT INTO Sale
(
    customer_id,
    sale_date,
    payment_method,
    discount_offered,
    total_net_amount
)
VALUES

(1,'2026-06-01','UPI',100.00,7500.00),
(2,'2026-06-02','Cash',0.00,3800.00),
(3,'2026-06-03','Card',200.00,6900.00),
(4,'2026-06-04','UPI',150.00,5550.00),
(5,'2026-06-05','Cash',0.00,4300.00),

(6,'2026-06-06','Card',250.00,7150.00),
(7,'2026-06-07','UPI',0.00,3900.00),
(8,'2026-06-08','Cash',100.00,2800.00),
(9,'2026-06-09','Card',0.00,650.00),
(10,'2026-06-10','UPI',300.00,8200.00),

(11,'2026-06-11','Cash',150.00,5050.00),
(12,'2026-06-12','Card',0.00,3700.00),
(13,'2026-06-13','UPI',100.00,3200.00),
(14,'2026-06-14','Cash',200.00,4700.00),
(15,'2026-06-15','Card',0.00,4900.00),

(16,'2026-06-16','UPI',150.00,6050.00),
(17,'2026-06-17','Cash',0.00,3800.00),
(18,'2026-06-18','Card',100.00,4200.00),
(19,'2026-06-19','UPI',250.00,7350.00),
(20,'2026-06-20','Cash',0.00,6500.00);

-- ==========================================
-- SALE ITEMS (60)
-- ==========================================

INSERT INTO Sale_Item
(sale_id, quantity, variant_id, selling_price)
VALUES

-- Sale 1
(1,1,1,3800),
(1,1,8,1900),
(1,1,22,2900),

-- Sale 2
(2,1,2,3800),

-- Sale 3
(3,1,4,4500),
(3,1,28,650),
(3,1,37,1450),

-- Sale 4
(4,1,13,4300),
(4,1,30,650),
(4,1,40,3300),

-- Sale 5
(5,1,15,4300),

-- Sale 6
(6,1,16,2200),
(6,1,31,3600),
(6,1,37,1450),

-- Sale 7
(7,1,19,3900),

-- Sale 8
(8,1,22,2900),

-- Sale 9
(9,1,28,650),

-- Sale 10
(10,1,43,4900),
(10,1,4,4500),
(10,1,10,3400),

-- Sale 11
(11,1,25,2100),
(11,1,16,2200),
(11,1,30,650),

-- Sale 12
(12,1,34,3700),

-- Sale 13
(13,1,43,4900),

-- Sale 14
(14,1,40,3300),
(14,1,22,2900),

-- Sale 15
(15,1,43,4900),

-- Sale 16
(16,1,1,3800),
(16,1,19,3900),
(16,1,28,650),

-- Sale 17
(17,1,3,3800),

-- Sale 18
(18,1,13,4300),

-- Sale 19
(19,1,31,3600),
(19,1,4,4500),
(19,1,30,650),

-- Sale 20
(20,1,5,4500),
(20,1,17,2200),
(20,1,37,1450);

INSERT INTO Sale_Item
(sale_id, quantity, variant_id, selling_price)
VALUES
(20,1,39,1450);
