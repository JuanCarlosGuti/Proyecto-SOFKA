# Reto Técnico Java - Arquitectura de Microservicios (SemiSenior)

## 🎯 Objetivo
Implementar una solución con **2 microservicios** que cumpla con los requerimientos del nivel SemiSenior:
- **F1, F2, F3, F4, F5** (obligatorias)
- **F6** (deseable)
- **Comunicación asíncrona** entre microservicios

## 🏗️ Arquitectura

### Microservicios
1. **Customer Service** (Puerto 8081)
   - Gestión de Clientes (F1)
   - Gestión de Personas (F2)
   - Validación de identidad

2. **Account Service** (Puerto 8082)
   - Gestión de Cuentas (F3)
   - Gestión de Movimientos (F4)
   - Consultas y Reportes (F5)
   - Validaciones de Negocio (F6)

### Componentes de Infraestructura
- **API Gateway** (Puerto 8080) - Punto de entrada único
- **Service Discovery** (Puerto 8761) - Eureka Server
- **Message Broker** (Puerto 5672) - RabbitMQ para comunicación asíncrona

## 🚀 Cómo Ejecutar

### Opción 1: Docker Compose (Recomendado)
```bash
docker-compose up -d
```

### Opción 2: Ejecución Manual
```bash
# 1. Iniciar Service Discovery
cd service-discovery && mvn spring-boot:run

# 2. Iniciar Customer Service
cd customer-service && mvn spring-boot:run

# 3. Iniciar Account Service
cd account-service && mvn spring-boot:run

# 4. Iniciar API Gateway
cd api-gateway && mvn spring-boot:run
```

## 📋 Funcionalidades Implementadas

### F1: Gestión de Clientes
- ✅ CRUD completo de clientes
- ✅ Estados de cliente (ACTIVE, INACTIVE, SUSPENDED)
- ✅ Validaciones de negocio

### F2: Gestión de Personas
- ✅ CRUD completo de personas
- ✅ Validación de documentos de identidad
- ✅ Información de contacto

### F3: Gestión de Cuentas
- ✅ CRUD completo de cuentas bancarias
- ✅ Tipos de cuenta (SAVINGS, CHECKING, BUSINESS)
- ✅ Estados de cuenta (ACTIVE, INACTIVE, BLOCKED)

### F4: Gestión de Movimientos
- ✅ Registro de transacciones
- ✅ Tipos de movimiento (DEPOSIT, WITHDRAWAL, TRANSFER)
- ✅ Historial de operaciones

### F5: Consultas y Reportes
- ✅ Consulta de saldos
- ✅ Historial de movimientos
- ✅ Reportes de transacciones

### F6: Validaciones de Negocio (Deseable)
- ✅ Validación de límites de transacción
- ✅ Verificación de fondos disponibles
- ✅ Reglas de negocio bancarias

## 🔄 Comunicación Asíncrona

### Eventos Principales
- `CustomerCreated` - Cliente creado
- `AccountOpened` - Cuenta abierta
- `TransactionProcessed` - Transacción procesada
- `BalanceUpdated` - Saldo actualizado

## 🌐 URLs de Acceso

- **API Gateway**: http://localhost:8080
- **Customer Service**: http://localhost:8081
- **Account Service**: http://localhost:8082
- **Service Discovery**: http://localhost:8761
- **Swagger UI**: http://localhost:8080/swagger-ui.html

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Cloud 2023.0.0**
- **Spring Cloud Gateway**
- **Netflix Eureka**
- **Spring Cloud Stream**
- **RabbitMQ**
- **H2 Database**
- **Docker & Docker Compose**
- **Swagger/OpenAPI 3**

## 📁 Estructura del Proyecto

```
sofka/
├── api-gateway/                 # API Gateway
├── customer-service/            # Microservicio 1: Cliente + Persona
├── account-service/             # Microservicio 2: Cuenta + Movimientos
├── service-discovery/           # Eureka Server
├── docker-compose.yml          # Orquestación de servicios
└── README.md                   # Este archivo
```

## 🧪 Pruebas

### Pruebas Unitarias
```bash
mvn test
```

### Pruebas de Integración
```bash
mvn verify
```

### Pruebas de Carga
```bash
# Usar herramientas como JMeter o Artillery
```

## 📊 Monitoreo

- **Health Checks**: `/actuator/health`
- **Metrics**: `/actuator/metrics`
- **Service Discovery**: http://localhost:8761

## 🔧 Configuración

### Variables de Entorno
- `SPRING_PROFILES_ACTIVE=dev`
- `EUREKA_SERVER_URL=http://localhost:8761/eureka`
- `RABBITMQ_HOST=localhost`
- `RABBITMQ_PORT=5672`

## 🚀 Despliegue

### Desarrollo
```bash
docker-compose -f docker-compose.dev.yml up
```

### Producción
```bash
docker-compose -f docker-compose.prod.yml up -d
```

---

**¡Proyecto implementado con arquitectura de microservicios nivel SemiSenior!** 🎉
