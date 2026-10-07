-- Esquema compatible con PostgreSQL y con H2 (usado en los tests)
DROP TABLE IF EXISTS productos;

CREATE TABLE productos (
    id     INT PRIMARY KEY,
    nombre VARCHAR(100)   NOT NULL,
    precio NUMERIC(10, 2) NOT NULL,
    stock  INT            NOT NULL
);
