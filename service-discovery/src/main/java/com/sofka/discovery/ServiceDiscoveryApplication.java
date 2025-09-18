package com.sofka.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Aplicación principal del Service Discovery
 * 
 * Eureka Server para el descubrimiento de servicios en la arquitectura de microservicios.
 * Permite que los microservicios se registren y descubran entre sí.
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscoveryApplication.class, args);
    }
}
