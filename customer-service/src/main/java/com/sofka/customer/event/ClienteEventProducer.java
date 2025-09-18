package com.sofka.customer.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClienteEventProducer {
    
    private final RabbitTemplate rabbitTemplate;
    
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    
    public void publishClienteCreatedEvent(ClienteCreatedEvent event) {
        try {
            log.info("Publishing ClienteCreatedEvent: {}", event);
            rabbitTemplate.convertAndSend(exchangeName, routingKey, event);
            log.info("ClienteCreatedEvent published successfully");
        } catch (Exception e) {
            log.error("Error publishing ClienteCreatedEvent: {}", e.getMessage(), e);
        }
    }
}

