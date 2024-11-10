INSERT INTO product (code, name, description, image, category, price, quantity, internal_reference, shell_id, inventory_status, rating, created_at, updated_at)
VALUES
  ('P001', 'Product 1', 'Description of Product 1', 'product1.jpg', 'Accessories', 19.99, 100, 'IR001', 1, 'INSTOCK', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('P002', 'Product 2', 'Description of Product 2', 'product2.jpg', 'Fitness', 29.99, 50, 'IR002', 2, 'INSTOCK', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('P003', 'Product 3', 'Description of Product 3', 'product3.jpg', 'Clothing', 59.99, 50, 'IR003', 3, 'LOWSTOCK', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('P004', 'Product 4', 'Description of Product 4', 'product4.jpg', 'Electronics', 129.99, 50, 'IR004', 4, 'OUTOFSTOCK', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);