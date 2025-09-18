package com.sofka.customer.service;

import com.sofka.customer.dto.ClienteDTO;
import com.sofka.customer.exception.ResourceConflictException;
import com.sofka.customer.exception.ResourceNotFoundException;
import com.sofka.customer.mapper.ClienteMapper;
import com.sofka.customer.model.Cliente;
import com.sofka.customer.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteService clienteService;

    private ClienteDTO clienteDTO;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setClienteId("CLI001");
        clienteDTO.setNombre("Jose Lema");
        clienteDTO.setGenero("M");
        clienteDTO.setEdad(35);
        clienteDTO.setIdentificacion("1234567890");
        clienteDTO.setDireccion("Otavalo sn y principal");
        clienteDTO.setTelefono("098254785");
        clienteDTO.setContrasena("1234");
        clienteDTO.setEstado(true);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setClienteId("CLI001");
        cliente.setNombre("Jose Lema");
        cliente.setGenero("M");
        cliente.setEdad(35);
        cliente.setIdentificacion("1234567890");
        cliente.setDireccion("Otavalo sn y principal");
        cliente.setTelefono("098254785");
        cliente.setContrasena("1234");
        cliente.setEstado(true);
    }

    @Test
    void crearCliente_DeberiaCrearClienteExitosamente() {
        // Given
        when(clienteRepository.existsByClienteId(anyString())).thenReturn(false);
        when(clienteRepository.existsByIdentificacion(anyString())).thenReturn(false);
        when(clienteMapper.toEntity(any(ClienteDTO.class))).thenReturn(cliente);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteMapper.toDTO(any(Cliente.class))).thenReturn(clienteDTO);

        // When
        ClienteDTO resultado = clienteService.crearCliente(clienteDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("CLI001", resultado.getClienteId());
        assertEquals("Jose Lema", resultado.getNombre());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void crearCliente_DeberiaLanzarExcepcionCuandoClienteIdYaExiste() {
        // Given
        when(clienteRepository.existsByClienteId(anyString())).thenReturn(true);

        // When & Then
        ResourceConflictException exception = assertThrows(ResourceConflictException.class, () -> {
            clienteService.crearCliente(clienteDTO);
        });

        assertEquals("Ya existe un cliente con el ID: CLI001", exception.getMessage());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void obtenerClientePorId_DeberiaRetornarClienteCuandoExiste() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDTO(any(Cliente.class))).thenReturn(clienteDTO);

        // When
        ClienteDTO resultado = clienteService.obtenerClientePorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals("CLI001", resultado.getClienteId());
        assertEquals("Jose Lema", resultado.getNombre());
    }

    @Test
    void obtenerClientePorId_DeberiaLanzarExcepcionCuandoNoExiste() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.obtenerClientePorId(1L);
        });

        assertEquals("Cliente no encontrado con ID: 1", exception.getMessage());
    }

    @Test
    void obtenerClientesActivos_DeberiaRetornarSoloClientesActivos() {
        // Given
        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setClienteId("CLI002");
        cliente2.setEstado(true);

        ClienteDTO clienteDTO2 = new ClienteDTO();
        clienteDTO2.setClienteId("CLI002");
        clienteDTO2.setEstado(true);

        List<Cliente> clientesActivos = Arrays.asList(cliente, cliente2);
        List<ClienteDTO> clientesDTOActivos = Arrays.asList(clienteDTO, clienteDTO2);

        when(clienteRepository.findByEstado(true)).thenReturn(clientesActivos);
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);
        when(clienteMapper.toDTO(cliente2)).thenReturn(clienteDTO2);

        // When
        List<ClienteDTO> resultado = clienteService.obtenerClientesActivos();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(ClienteDTO::getEstado));
    }

    @Test
    void eliminarCliente_DeberiaEliminarClienteCuandoExiste() {
        // Given
        when(clienteRepository.existsById(1L)).thenReturn(true);

        // When
        clienteService.eliminarCliente(1L);

        // Then
        verify(clienteRepository).deleteById(1L);
    }

    @Test
    void eliminarCliente_DeberiaLanzarExcepcionCuandoNoExiste() {
        // Given
        when(clienteRepository.existsById(1L)).thenReturn(false);

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.eliminarCliente(1L);
        });

        assertEquals("Cliente no encontrado con ID: 1", exception.getMessage());
        verify(clienteRepository, never()).deleteById(any());
    }
}
