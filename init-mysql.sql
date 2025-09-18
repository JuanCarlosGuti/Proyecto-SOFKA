-- Script de inicialización para MySQL
-- Ejecutar este script para crear las bases de datos y usuarios

-- Crear bases de datos
CREATE DATABASE IF NOT EXISTS customer_db;
CREATE DATABASE IF NOT EXISTS account_db;

-- Crear usuario para los microservicios (opcional, puedes usar root)
-- CREATE USER 'microservices'@'localhost' IDENTIFIED BY 'Wire2681';
-- GRANT ALL PRIVILEGES ON customer_db.* TO 'microservices'@'localhost';
-- GRANT ALL PRIVILEGES ON account_db.* TO 'microservices'@'localhost';
-- FLUSH PRIVILEGES;

-- Verificar que las bases de datos se crearon
SHOW DATABASES;
