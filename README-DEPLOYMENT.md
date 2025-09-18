# 🚀 Deployment Guide - Sofka Microservices

## 📋 Prerequisites

- Docker
- Docker Compose
- Maven 3.6+
- Java 17

## 🏗️ Build and Deploy

### 1. Build all microservices

```bash
# Build Service Discovery
cd service-discovery
mvn clean package -DskipTests

# Build Customer Service
cd ../customer-service
mvn clean package -DskipTests

# Build Account Service
cd ../account-service
mvn clean package -DskipTests

# Build API Gateway
cd ../api-gateway
mvn clean package -DskipTests

cd ..
```

### 2. Deploy with Docker Compose

```bash
# Start all services
docker-compose up -d

# Check status
docker-compose ps

# View logs
docker-compose logs -f
```

### 3. Access Services

- **API Gateway:** http://localhost:8080
- **Customer Service:** http://localhost:8081
- **Account Service:** http://localhost:8082
- **Service Discovery:** http://localhost:8761
- **RabbitMQ Management:** http://localhost:15672 (guest/guest)
- **MySQL:** localhost:3306 (root/Wire2681)

## 🧪 Testing

### Run Tests

```bash
# Unit Tests
cd customer-service
mvn test

cd ../account-service
mvn test

# Integration Tests
mvn test -Dtest=*IntegrationTest
```

### Test API Endpoints

Use the provided Postman collection: `Sofka-Microservices.postman_collection.json`

## 📊 Monitoring

- **Eureka Dashboard:** http://localhost:8761
- **RabbitMQ Management:** http://localhost:15672
- **Swagger Documentation:**
  - Customer Service: http://localhost:8081/swagger-ui.html
  - Account Service: http://localhost:8082/swagger-ui.html

## 🔧 Troubleshooting

### Check Container Logs

```bash
docker-compose logs service-discovery
docker-compose logs customer-service
docker-compose logs account-service
docker-compose logs api-gateway
```

### Restart Services

```bash
docker-compose restart customer-service
docker-compose restart account-service
```

### Clean Up

```bash
docker-compose down
docker-compose down -v  # Remove volumes
```
