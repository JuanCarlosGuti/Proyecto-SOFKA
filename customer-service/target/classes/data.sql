-- Datos iniciales para Customer Service
-- Insertar clientes de ejemplo (que heredan de Persona)
INSERT INTO clientes (nombre, genero, edad, identificacion, direccion, telefono, cliente_id, contrasena, estado, dtype, created_at, updated_at) VALUES
('Jose Lema', 'M', 35, '1234567890', 'Otavalo sn y principal', '098254785', 'CLI001', '1234', TRUE, 'Cliente', NOW(), NOW()),
('Marianela Montalvo', 'F', 28, '1234567891', 'Amazonas y NNUU', '097548965', 'CLI002', '5678', TRUE, 'Cliente', NOW(), NOW()),
('Juan Osorio', 'M', 42, '1234567892', '13 junio y Equinoccial', '098874587', 'CLI003', '1245', TRUE, 'Cliente', NOW(), NOW());
