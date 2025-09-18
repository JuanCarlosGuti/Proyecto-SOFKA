#!/bin/bash

echo "🚀 Iniciando microservicios con MySQL..."

# Configurar Java 17
export JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home
export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"

echo "☕ Usando Java 17: $(java -version 2>&1 | head -1)"

# Verificar que MySQL esté ejecutándose
echo "📋 Verificando conexión a MySQL..."
/usr/local/mysql-commercial-9.4.0-macos15-arm64/bin/mysql -u root -pWire2681 -e "SELECT 1;" 2>/dev/null
if [ $? -ne 0 ]; then
    echo "❌ Error: No se puede conectar a MySQL. Verifica que esté ejecutándose."
    echo "💡 Comando para iniciar MySQL: sudo /usr/local/mysql/support-files/mysql.server start"
    exit 1
fi

echo "✅ MySQL está ejecutándose correctamente"

# Inicializar bases de datos
echo "📋 Inicializando bases de datos..."
/usr/local/mysql-commercial-9.4.0-macos15-arm64/bin/mysql -u root -pWire2681 < init-mysql.sql

# Iniciar Service Discovery
echo "🚀 Iniciando Service Discovery (Eureka)..."
cd service-discovery
mvn spring-boot:run &
EUREKA_PID=$!
cd ..

# Esperar a que Eureka esté listo
echo "⏳ Esperando a que Eureka esté listo..."
sleep 30

# Iniciar Customer Service
echo "🚀 Iniciando Customer Service..."
cd customer-service
mvn spring-boot:run &
CUSTOMER_PID=$!
cd ..

# Esperar un poco
sleep 15

# Iniciar Account Service
echo "🚀 Iniciando Account Service..."
cd account-service
mvn spring-boot:run &
ACCOUNT_PID=$!
cd ..

# Esperar un poco
sleep 15

# Iniciar API Gateway
echo "🚀 Iniciando API Gateway..."
cd api-gateway
mvn spring-boot:run &
GATEWAY_PID=$!
cd ..

echo "✅ Todos los microservicios están ejecutándose!"
echo ""
echo "📋 URLs disponibles:"
echo "   🔍 Eureka Dashboard: http://localhost:8761"
echo "   👥 Customer Service: http://localhost:8081"
echo "   💰 Account Service: http://localhost:8082"
echo "   🌐 API Gateway: http://localhost:8080"
echo "   📚 Swagger Customer: http://localhost:8081/swagger-ui.html"
echo "   📚 Swagger Account: http://localhost:8082/swagger-ui.html"
echo "   📚 Swagger Gateway: http://localhost:8080/swagger-ui.html"
echo ""
echo "🛑 Para detener todos los servicios, presiona Ctrl+C"

# Función para limpiar procesos al salir
cleanup() {
    echo ""
    echo "🛑 Deteniendo microservicios..."
    kill $EUREKA_PID $CUSTOMER_PID $ACCOUNT_PID $GATEWAY_PID 2>/dev/null
    echo "✅ Microservicios detenidos"
    exit 0
}

# Capturar Ctrl+C
trap cleanup SIGINT

# Mantener el script ejecutándose
wait
