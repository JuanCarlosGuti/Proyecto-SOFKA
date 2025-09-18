-- Schema para Account Service con MySQL
-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS account_db;
USE account_db;

-- Tabla de Cuentas
CREATE TABLE IF NOT EXISTS cuentas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_cuenta VARCHAR(20) NOT NULL UNIQUE,
    tipo_cuenta ENUM('AHORRO', 'CORRIENTE') NOT NULL,
    saldo_inicial DECIMAL(15,2) NOT NULL CHECK (saldo_inicial >= 0),
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    cliente_id VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de Movimientos
CREATE TABLE IF NOT EXISTS movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha TIMESTAMP NOT NULL,
    tipo_movimiento ENUM('DEPOSITO', 'RETIRO') NOT NULL,
    valor DECIMAL(15,2) NOT NULL CHECK (valor > 0),
    saldo DECIMAL(15,2) NOT NULL,
    numero_cuenta VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices para mejorar el rendimiento
-- Nota: Los índices se crean automáticamente por las restricciones UNIQUE
-- CREATE INDEX idx_cuentas_numero_cuenta ON cuentas(numero_cuenta);
-- CREATE INDEX idx_cuentas_cliente_id ON cuentas(cliente_id);
-- CREATE INDEX idx_cuentas_estado ON cuentas(estado);
-- CREATE INDEX idx_movimientos_numero_cuenta ON movimientos(numero_cuenta);
-- CREATE INDEX idx_movimientos_fecha ON movimientos(fecha);
-- CREATE INDEX idx_movimientos_tipo ON movimientos(tipo_movimiento);