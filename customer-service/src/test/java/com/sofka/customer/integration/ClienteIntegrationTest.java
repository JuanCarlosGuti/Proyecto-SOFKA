package com.sofka.customer.integration;

import com.sofka.customer.dto.ClienteDTO;
import com.sofka.customer.model.Cliente;
import com.sofka.customer.repository.ClienteRepository;
import com.sofka.customer.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ClienteIntegrationTest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Test Cliente Integration");
        clienteDTO.setGenero("M");
        clienteDTO.setEdad(30);
        clienteDTO.setIdentificacion("1234567890");
        clienteDTO.setDireccion("Test Address 123");
        clienteDTO.setTelefono("0999999999");
        clienteDTO.setClienteId("CLIINT001");
        clienteDTO.setContrasena("test123");
        clienteDTO.setEstado(true);
    }

    @Test
    void testCrearClienteIntegration() {
        // Given
        assertTrue(clienteRepository.findAll().isEmpty());

        // When
        ClienteDTO clienteCreado = clienteService.crearCliente(clienteDTO);

        // Then
        assertNotNull(clienteCreado);
        assertNotNull(clienteCreado.getId());
        assertEquals("Test Cliente Integration", clienteCreado.getNombre());
        assertEquals("CLIINT001", clienteCreado.getClienteId());
        assertEquals("1234567890", clienteCreado.getIdentificacion());

        // Verificar que se guardó en la base de datos
        assertFalse(clienteRepository.findAll().isEmpty());
        Cliente clienteGuardado = clienteRepository.findById(clienteCreado.getId()).orElse(null);
        assertNotNull(clienteGuardado);
        assertEquals("Test Cliente Integration", clienteGuardado.getNombre());
    }

    @Test
    void testObtenerClientePorIdIntegration() {
        // Given
        ClienteDTO clienteCreado = clienteService.crearCliente(clienteDTO);

        // When
        ClienteDTO clienteObtenido = clienteService.obtenerClientePorId(clienteCreado.getId());

        // Then
        assertNotNull(clienteObtenido);
        assertEquals(clienteCreado.getId(), clienteObtenido.getId());
        assertEquals("Test Cliente Integration", clienteObtenido.getNombre());
    }

    @Test
    void testActualizarClienteIntegration() {
        // Given
        ClienteDTO clienteCreado = clienteService.crearCliente(clienteDTO);
        clienteDTO.setNombre("Cliente Actualizado");
        clienteDTO.setEdad(31);

        // When
        ClienteDTO clienteActualizado = clienteService.actualizarCliente(clienteCreado.getId(), clienteDTO);

        // Then
        assertNotNull(clienteActualizado);
        assertEquals("Cliente Actualizado", clienteActualizado.getNombre());
        assertEquals(31, clienteActualizado.getEdad());

        // Verificar en la base de datos
        Cliente clienteEnBD = clienteRepository.findById(clienteCreado.getId()).orElse(null);
        assertNotNull(clienteEnBD);
        assertEquals("Cliente Actualizado", clienteEnBD.getNombre());
    }

    @Test
    void testCambiarEstadoClienteIntegration() {
        // Given
        ClienteDTO clienteCreado = clienteService.crearCliente(clienteDTO);

        // When
        ClienteDTO clienteDesactivado = clienteService.cambiarEstadoCliente(clienteCreado.getId(), false);

        // Then
        assertNotNull(clienteDesactivado);
        assertFalse(clienteDesactivado.getEstado());

        // Verificar en la base de datos
        Cliente clienteEnBD = clienteRepository.findById(clienteCreado.getId()).orElse(null);
        assertNotNull(clienteEnBD);
        assertFalse(clienteEnBD.getEstado());
    }
}
