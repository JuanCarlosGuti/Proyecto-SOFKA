package com.sofka.account.integration;

import com.sofka.account.dto.CuentaDTO;
import com.sofka.account.dto.MovimientoRequestDTO;
import com.sofka.account.model.TipoMovimiento;
import com.sofka.account.model.TipoCuenta;
import com.sofka.account.service.CuentaService;
import com.sofka.account.service.MovimientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MovimientoIntegrationTest {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private CuentaService cuentaService;

    private CuentaDTO cuentaDTO;
    private MovimientoRequestDTO movimientoRequestDTO;

    @BeforeEach
    void setUp() {
        // Crear cuenta de prueba
        cuentaDTO = new CuentaDTO();
        cuentaDTO.setNumeroCuenta("1234567890");
        cuentaDTO.setTipoCuenta(TipoCuenta.AHORRO);
        cuentaDTO.setSaldoInicial(new BigDecimal("1000.00"));
        cuentaDTO.setEstado(true);
        cuentaDTO.setClienteId("CLIINT001");

        // Crear movimiento de prueba
        movimientoRequestDTO = new MovimientoRequestDTO();
        movimientoRequestDTO.setNumeroCuenta("1234567890");
        movimientoRequestDTO.setTipoMovimiento(TipoMovimiento.CREDITO);
        movimientoRequestDTO.setValor(new BigDecimal("500.00"));
    }

    @Test
    void testRegistrarMovimientoCreditoIntegration() {
        // Given
        CuentaDTO cuentaCreada = cuentaService.crearCuenta(cuentaDTO);

        // When
        var movimientoRegistrado = movimientoService.registrarMovimiento(movimientoRequestDTO);

        // Then
        assertNotNull(movimientoRegistrado);
        assertEquals("1234567890", movimientoRegistrado.getNumeroCuenta());
        assertEquals(TipoMovimiento.CREDITO, movimientoRegistrado.getTipoMovimiento());
        assertEquals(new BigDecimal("500.00"), movimientoRegistrado.getValor());
        assertEquals(new BigDecimal("1500.00"), movimientoRegistrado.getSaldo());
    }

    @Test
    void testRegistrarMovimientoDebitoIntegration() {
        // Given
        CuentaDTO cuentaCreada = cuentaService.crearCuenta(cuentaDTO);
        movimientoRequestDTO.setTipoMovimiento(TipoMovimiento.DEBITO);
        movimientoRequestDTO.setValor(new BigDecimal("200.00"));

        // When
        var movimientoRegistrado = movimientoService.registrarMovimiento(movimientoRequestDTO);

        // Then
        assertNotNull(movimientoRegistrado);
        assertEquals("1234567890", movimientoRegistrado.getNumeroCuenta());
        assertEquals(TipoMovimiento.DEBITO, movimientoRegistrado.getTipoMovimiento());
        assertEquals(new BigDecimal("200.00"), movimientoRegistrado.getValor());
        assertEquals(new BigDecimal("800.00"), movimientoRegistrado.getSaldo());
    }

    @Test
    void testSaldoInsuficienteIntegration() {
        // Given
        CuentaDTO cuentaCreada = cuentaService.crearCuenta(cuentaDTO);
        movimientoRequestDTO.setTipoMovimiento(TipoMovimiento.DEBITO);
        movimientoRequestDTO.setValor(new BigDecimal("1500.00")); // Más que el saldo inicial

        // When & Then
        assertThrows(Exception.class, () -> {
            movimientoService.registrarMovimiento(movimientoRequestDTO);
        });
    }

    @Test
    void testObtenerMovimientosPorCuentaIntegration() {
        // Given
        CuentaDTO cuentaCreada = cuentaService.crearCuenta(cuentaDTO);
        movimientoService.registrarMovimiento(movimientoRequestDTO);

        // When
        var movimientos = movimientoService.obtenerMovimientosPorCuenta("1234567890");

        // Then
        assertNotNull(movimientos);
        assertFalse(movimientos.isEmpty());
        assertEquals(1, movimientos.size());
        assertEquals(TipoMovimiento.CREDITO, movimientos.get(0).getTipoMovimiento());
    }
}
