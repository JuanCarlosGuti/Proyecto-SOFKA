package com.sofka.account.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClienteEventConsumer {
    
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleClienteCreatedEvent(ClienteCreatedEvent event) {
        try {
            log.info("Received ClienteCreatedEvent: {}", event);
            
            // Aquí puedes agregar lógica para crear una cuenta por defecto
            // o realizar otras acciones cuando se crea un nuevo cliente
            log.info("Processing new client: {} with ID: {}", event.getNombre(), event.getClienteId());
            
            // TODO: Implementar lógica de negocio específica
            // Por ejemplo: crear una cuenta de ahorros por defecto para el nuevo cliente
            
        } catch (Exception e) {
            log.error("Error processing ClienteCreatedEvent: {}", e.getMessage(), e);
        }
    }
}
