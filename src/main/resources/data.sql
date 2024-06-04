-- Create ADMIN
INSERT INTO users (username, password, roles)
VALUES
    ('admin', '$2a$10$CrE6gSeUAQtqAclAE2EBJOxeYPW3snw...xq5m/kq8wRL2EqNI3xW', 'ADMIN');

-- Create products
INSERT INTO products (name, iconlink, price, quantity)
VALUES
    ('Carrot', '/images/icons/carrot.png', 5, 30),
    ('Banana', '/images/icons/banana.png', 10, 25),
    ('Apple', '/images/icons/apple.png', 5, 20),
    ('LOKA 0.5L', '/images/icons/loka.png', 16, 45),
    ('Coca Cola 1.5L', '/images/icons/cocacola.png', 25, 50),
    ('Corn Flakes', '/images/icons/cornflakes.png', 35, 25),
    ('Bread', '/images/icons/bread.png', 25, 10),
    ('Minced Meat 1kg', '/images/icons/mincedmeat.png', 60, 20),
    ('Taco Spice x3', '/images/icons/tacospice.png', 30, 25),
    ('Chili Peppers', '/images/icons/chilipepper.png', 20, 15),
    ('Salsa', '/images/icons/salsa.png', 20, 30),
    ('Milk', '/images/icons/milk.png', 12, 60);