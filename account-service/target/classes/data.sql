-- Datos iniciales para Account Service
-- Insertar cuentas de ejemplo
INSERT INTO cuentas (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id, created_at, updated_at) VALUES
('478758', 'AHORRO', 2000.00, TRUE, 'CLI001', NOW(), NOW()),
('225487', 'CORRIENTE', 100.00, TRUE, 'CLI002', NOW(), NOW()),
('495878', 'AHORRO', 0.00, TRUE, 'CLI003', NOW(), NOW()),
('496825', 'AHORRO', 540.00, TRUE, 'CLI002', NOW(), NOW()),
('585545', 'CORRIENTE', 1000.00, TRUE, 'CLI001', NOW(), NOW());
