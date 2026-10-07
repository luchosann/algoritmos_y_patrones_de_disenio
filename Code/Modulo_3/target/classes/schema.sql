-- Esquema para el ejercicio opcional (DAOs JDBC sobre PostgreSQL)
CREATE TABLE IF NOT EXISTS estudiantes (
    ci     CHAR(8)      PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email  VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS cursos (
    codigo      VARCHAR(10)  PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    cupo_maximo INT          NOT NULL CHECK (cupo_maximo > 0)
);

CREATE TABLE IF NOT EXISTS inscripciones (
    ci           CHAR(8)     NOT NULL REFERENCES estudiantes (ci) ON DELETE CASCADE,
    codigo_curso VARCHAR(10) NOT NULL REFERENCES cursos (codigo) ON DELETE CASCADE,
    PRIMARY KEY (ci, codigo_curso)
);
