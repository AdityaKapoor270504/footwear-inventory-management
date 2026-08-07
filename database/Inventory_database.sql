CREATE TABLE Supplier (
	supplier_id SERIAL PRIMARY KEY,
	supplier_name VARCHAR (100) NOT NULL,
	contact_number VARCHAR (15) NOT NULL,
	email_id VARCHAR (100),
	supplier_address TEXT
);

CREATE TABLE Sale (
	sale_id SERIAL PRIMARY KEY,
	customer_id INTEGER NOT NULL,
	sale_date DATE NOT NULL,
	payment_method TEXT NOT NULL,
	discount_offered DECIMAL (10, 2),
	total_net_amount DECIMAL (10, 2) NOT NULL
);

CREATE TABLE Customer (
	customer_id SERIAL PRIMARY KEY,
	customer_name VARCHAR (50) NOT NULL,
	customer_contact_number VARCHAR (15) NOT NULL
);

CREATE TABLE Product (
	product_id SERIAL PRIMARY KEY,
	product_name TEXT NOT NULL,
	product_brand VARCHAR (15) NOT NULL,
	product_category VARCHAR (15) NOT NULL,
	gender CHAR (1) NOT NULL
	CHECK (gender IN ('M', 'F')),
	created_at DATE NOT NULL DEFAULT CURRENT_DATE,
	updated_at DATE NOT NULL DEFAULT CURRENT_DATE,
	cost_price DECIMAL (10, 2) NOT NULL,
	selling_price DECIMAL (10, 2) NOT NULL
);

CREATE TABLE Inventory (
	inventory_id SERIAL PRIMARY KEY,
	variant_id INTEGER NOT NULL,
	quantity_in_stock INTEGER NOT NULL
	CHECK (quantity_in_stock >= 0),
	created_at DATE NOT NULL,
	updated_at DATE NOT NULL
);

CREATE TABLE Product_variant (
	variant_id SERIAL PRIMARY KEY,
	product_id INTEGER NOT NULL,
	size_of_product INTEGER NOT NULL
	CHECK (size_of_product > 0),
	colour VARCHAR (15) NOT NULL
);

CREATE TABLE Purchase_item (
	purchase_item_id SERIAL PRIMARY KEY,
	purchase_id INTEGER NOT NULL,
	quantity INTEGER NOT NULL
	CHECK (quantity > 0),
	variant_id INTEGER NOT NULL,
	cost_price DECIMAL (10, 2) NOT NULL
);

CREATE TABLE Sale_item (
	sale_item_id SERIAL PRIMARY KEY,
	sale_id INTEGER NOT NULL,
	quantity INTEGER NOT NULL
	CHECK (quantity > 0),
	variant_id INTEGER NOT NULL,
	selling_price DECIMAL (10, 2) NOT NULL	
);

CREATE TABLE Purchase (
	purchase_id SERIAL PRIMARY KEY,
	supplier_id INTEGER NOT NULL,
	purchase_date DATE NOT NULL,
	invoice_number VARCHAR (20) NOT NULL,
	payment_method TEXT,
	total_payment_amount DECIMAL (10, 2) NOT NULL
);

ALTER TABLE purchase
ADD CONSTRAINT foreign_key_purchase_supplier
FOREIGN KEY (supplier_id)
REFERENCES supplier (supplier_id);

ALTER TABLE product_variant
ADD CONSTRAINT foreign_key_variant_of_product
FOREIGN KEY (product_id)
REFERENCES product (product_id);

ALTER TABLE sale
ADD CONSTRAINT foreign_key_sale_customer
FOREIGN KEY (customer_id)
REFERENCES customer (customer_id);

ALTER TABLE inventory
ADD CONSTRAINT foreign_key_inventory_variant
FOREIGN KEY (variant_id)
REFERENCES product_variant (variant_id);

ALTER TABLE purchase_item
ADD CONSTRAINT foreign_key_purchasebill
FOREIGN KEY (purchase_id)
REFERENCES purchase (purchase_id),
ADD CONSTRAINT foreign_key_purchase_variant
FOREIGN KEY (variant_id)
REFERENCES product_variant (variant_id);

ALTER TABLE sale_item
ADD CONSTRAINT foreign_key_salebill
FOREIGN KEY (sale_id)
REFERENCES sale (sale_id),
ADD CONSTRAINT foreign_key_sale_variant
FOREIGN KEY (variant_id)
REFERENCES product_variant (variant_id);

ALTER TABLE product
ALTER COLUMN created_at SET DEFAULT CURRENT_DATE;

ALTER TABLE product
ALTER COLUMN updated_at SET DEFAULT CURRENT_DATE;

ALTER TABLE product_variant
DROP CONSTRAINT product_variant_size_of_product_check;
ALTER TABLE product_variant
ALTER COLUMN size_of_product TYPE VARCHAR(20)
USING size_of_product::VARCHAR;

ALTER TABLE Inventory
ALTER COLUMN created_at SET DEFAULT CURRENT_DATE;

ALTER TABLE Inventory
ALTER COLUMN updated_at SET DEFAULT CURRENT_DATE;
