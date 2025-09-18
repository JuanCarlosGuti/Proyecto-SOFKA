package com.sofka.customer.service;

import com.sofka.customer.dto.ClienteDTO;
import com.sofka.customer.event.ClienteCreatedEvent;
import com.sofka.customer.event.ClienteEventProducer;
import com.sofka.customer.exception.ResourceNotFoundException;
import com.sofka.customer.exception.ResourceConflictException;
import com.sofka.customer.mapper.ClienteMapper;
import com.sofka.customer.model.Cliente;
import com.sofka.customer.repository.ClienteRepository;
import com.sofka.customer.validation.ClienteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final ClienteEventProducer clienteEventProducer;
    private final ClienteValidator clienteValidator;

    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        // Validar datos del cliente
        clienteValidator.validarCreacionCliente(clienteDTO);
        
        if (clienteRepository.existsByClienteId(clienteDTO.getClienteId())) {
            throw new ResourceConflictException("Ya existe un cliente con el ID: " + clienteDTO.getClienteId());
        }

        if (clienteRepository.existsByIdentificacion(clienteDTO.getIdentificacion())) {
            throw new ResourceConflictException("Ya existe un cliente con la identificación: " + clienteDTO.getIdentificacion());
        }

        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        
        // Publicar evento de cliente creado
        ClienteCreatedEvent event = new ClienteCreatedEvent(
            clienteGuardado.getClienteId(),
            clienteGuardado.getNombre(),
            clienteGuardado.getIdentificacion(),
            clienteGuardado.getTelefono()
        );
        clienteEventProducer.publishClienteCreatedEvent(event);
        
        return clienteMapper.toDTO(clienteGuardado);
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> obtenerTodosLosClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> obtenerClientesActivos() {
        return clienteRepository.findByEstado(true)
                .stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteDTO obtenerClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        return clienteMapper.toDTO(cliente);
    }

    @Transactional(readOnly = true)
    public ClienteDTO obtenerClientePorClienteId(String clienteId) {
        Cliente cliente = clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID de cliente: " + clienteId));
        return clienteMapper.toDTO(cliente);
    }

    @Transactional(readOnly = true)
    public ClienteDTO obtenerClientePorIdentificacion(String identificacion) {
        Cliente cliente = clienteRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con identificación: " + identificacion));
        return clienteMapper.toDTO(cliente);
    }

    public ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));

        // Verificar si el nuevo clienteId ya existe en otro cliente
        if (!clienteExistente.getClienteId().equals(clienteDTO.getClienteId()) &&
            clienteRepository.existsByClienteId(clienteDTO.getClienteId())) {
            throw new ResourceConflictException("Ya existe un cliente con el ID: " + clienteDTO.getClienteId());
        }

        // Verificar si la nueva identificación ya existe en otro cliente
        if (!clienteExistente.getIdentificacion().equals(clienteDTO.getIdentificacion()) &&
            clienteRepository.existsByIdentificacion(clienteDTO.getIdentificacion())) {
            throw new ResourceConflictException("Ya existe un cliente con la identificación: " + clienteDTO.getIdentificacion());
        }

        clienteMapper.updateEntity(clienteDTO, clienteExistente);
        Cliente clienteActualizado = clienteRepository.save(clienteExistente);
        return clienteMapper.toDTO(clienteActualizado);
    }

    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }

    public ClienteDTO cambiarEstadoCliente(Long id, Boolean estado) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        
        cliente.setEstado(estado);
        Cliente clienteActualizado = clienteRepository.save(cliente);
        return clienteMapper.toDTO(clienteActualizado);
    }
}
