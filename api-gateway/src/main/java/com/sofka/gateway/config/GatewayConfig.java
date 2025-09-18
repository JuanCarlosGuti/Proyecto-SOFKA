package com.sofka.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Rutas para Customer Service
                .route("customer-service", r -> r.path("/api/clientes/**", "/api/personas/**")
                        .uri("lb://customer-service"))
                
                // Rutas para Account Service
                .route("account-service", r -> r.path("/api/cuentas/**", "/api/movimientos/**")
                        .uri("lb://account-service"))
                
                // Ruta para reportes (orquestación entre servicios)
                .route("reports-service", r -> r.path("/api/reportes/**")
                        .uri("lb://api-gateway"))
                
                .build();
    }
}
