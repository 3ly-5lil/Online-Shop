CREATE TABLE public.orders (
    id            BIGSERIAL     PRIMARY KEY,
    order_number  VARCHAR(255)  DEFAULT NULL,
    sku_code      VARCHAR(255),
    price         NUMERIC(19, 2),
    quantity      INTEGER
);