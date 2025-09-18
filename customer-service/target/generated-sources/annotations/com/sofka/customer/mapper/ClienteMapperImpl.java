package com.sofka.customer.mapper;

import com.sofka.customer.dto.ClienteDTO;
import com.sofka.customer.model.Cliente;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-18T13:47:43-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Homebrew)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public Cliente toEntity(ClienteDTO clienteDTO) {
        if ( clienteDTO == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setNombre( clienteDTO.getNombre() );
        cliente.setGenero( clienteDTO.getGenero() );
        cliente.setEdad( clienteDTO.getEdad() );
        cliente.setIdentificacion( clienteDTO.getIdentificacion() );
        cliente.setDireccion( clienteDTO.getDireccion() );
        cliente.setTelefono( clienteDTO.getTelefono() );
        cliente.setClienteId( clienteDTO.getClienteId() );
        cliente.setContrasena( clienteDTO.getContrasena() );
        cliente.setEstado( clienteDTO.getEstado() );

        return cliente;
    }

    @Override
    public ClienteDTO toDTO(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setId( cliente.getId() );
        clienteDTO.setNombre( cliente.getNombre() );
        clienteDTO.setGenero( cliente.getGenero() );
        clienteDTO.setEdad( cliente.getEdad() );
        clienteDTO.setIdentificacion( cliente.getIdentificacion() );
        clienteDTO.setDireccion( cliente.getDireccion() );
        clienteDTO.setTelefono( cliente.getTelefono() );
        clienteDTO.setClienteId( cliente.getClienteId() );
        clienteDTO.setContrasena( cliente.getContrasena() );
        clienteDTO.setEstado( cliente.getEstado() );
        clienteDTO.setCreatedAt( cliente.getCreatedAt() );
        clienteDTO.setUpdatedAt( cliente.getUpdatedAt() );

        return clienteDTO;
    }

    @Override
    public Cliente updateEntity(ClienteDTO clienteDTO, Cliente cliente) {
        if ( clienteDTO == null ) {
            return cliente;
        }

        cliente.setNombre( clienteDTO.getNombre() );
        cliente.setGenero( clienteDTO.getGenero() );
        cliente.setEdad( clienteDTO.getEdad() );
        cliente.setIdentificacion( clienteDTO.getIdentificacion() );
        cliente.setDireccion( clienteDTO.getDireccion() );
        cliente.setTelefono( clienteDTO.getTelefono() );
        cliente.setClienteId( clienteDTO.getClienteId() );
        cliente.setContrasena( clienteDTO.getContrasena() );
        cliente.setEstado( clienteDTO.getEstado() );

        return cliente;
    }
}
