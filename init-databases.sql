-- Script para crear ambas bases de datos
CREATE DATABASE IF NOT EXISTS customer_db;
CREATE DATABASE IF NOT EXISTS account_db;

-- Otorgar permisos al usuario
GRANT ALL PRIVILEGES ON customer_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON account_db.* TO 'root'@'%';
FLUSH PRIVILEGES;
