package com.sofka.customer.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCreatedEvent {
    private String clienteId;
    private String nombre;
    private String identificacion;
    private String telefono;
    private LocalDateTime timestamp;
    
    public ClienteCreatedEvent(String clienteId, String nombre, String identificacion, String telefono) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.timestamp = LocalDateTime.now();
    }
}

