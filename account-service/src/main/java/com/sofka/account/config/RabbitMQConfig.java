package com.sofka.account.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    
    @Value("${rabbitmq.queue.name}")
    private String queueName;
    
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    
    @Bean
    public TopicExchange clienteExchange() {
        return new TopicExchange(exchangeName);
    }
    
    @Bean
    public Queue clienteCreatedQueue() {
        return QueueBuilder.durable(queueName).build();
    }
    
    @Bean
    public Binding clienteCreatedBinding() {
        return BindingBuilder
                .bind(clienteCreatedQueue())
                .to(clienteExchange())
                .with(routingKey);
    }
}

