-- Schema para Customer Service con MySQL
-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS customer_db;
USE customer_db;

-- Tabla de Personas
CREATE TABLE IF NOT EXISTS personas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero ENUM('M', 'F', 'Otro') NOT NULL,
    edad INTEGER NOT NULL CHECK (edad >= 0 AND edad <= 150),
    identificacion VARCHAR(20) NOT NULL UNIQUE,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    dtype VARCHAR(31) NOT NULL DEFAULT 'Persona',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de Clientes (hereda de Persona)
CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero ENUM('M', 'F', 'Otro') NOT NULL,
    edad INTEGER NOT NULL CHECK (edad >= 0 AND edad <= 150),
    identificacion VARCHAR(20) NOT NULL UNIQUE,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    cliente_id VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    dtype VARCHAR(31) NOT NULL DEFAULT 'Cliente',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Índices para mejorar el rendimiento
-- Nota: Los índices se crean automáticamente por las restricciones UNIQUE
-- CREATE INDEX idx_personas_identificacion ON personas(identificacion);
-- CREATE INDEX idx_clientes_cliente_id ON clientes(cliente_id);
-- CREATE INDEX idx_clientes_identificacion ON clientes(identificacion);
-- CREATE INDEX idx_clientes_estado ON clientes(estado);