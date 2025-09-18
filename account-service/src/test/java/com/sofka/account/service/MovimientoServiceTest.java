package com.sofka.account.service;

import com.sofka.account.dto.CuentaDTO;
import com.sofka.account.dto.MovimientoDTO;
import com.sofka.account.dto.MovimientoRequestDTO;
import com.sofka.account.exception.BusinessException;
import com.sofka.account.exception.ResourceNotFoundException;
import com.sofka.account.mapper.MovimientoMapper;
import com.sofka.account.model.Movimiento;
import com.sofka.account.model.TipoCuenta;
import com.sofka.account.model.TipoMovimiento;
import com.sofka.account.repository.MovimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceTest {

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private MovimientoMapper movimientoMapper;

    @Mock
    private CuentaService cuentaService;

    @InjectMocks
    private MovimientoService movimientoService;

    private MovimientoDTO movimientoDTO;
    private Movimiento movimiento;
    private CuentaDTO cuentaDTO;

    @BeforeEach
    void setUp() {
        // Configurar CuentaDTO
        cuentaDTO = new CuentaDTO();
        cuentaDTO.setId(1L);
        cuentaDTO.setNumeroCuenta("478758");
        cuentaDTO.setTipoCuenta(TipoCuenta.AHORRO);
        cuentaDTO.setSaldoInicial(new BigDecimal("2000.00"));
        cuentaDTO.setEstado(true);
        cuentaDTO.setClienteId("CLI001");

        // Configurar MovimientoDTO
        movimientoDTO = new MovimientoDTO();
        movimientoDTO.setTipoMovimiento(TipoMovimiento.CREDITO);
        movimientoDTO.setValor(new BigDecimal("500.00"));
        movimientoDTO.setNumeroCuenta("478758");

        // Configurar Movimiento
        movimiento = new Movimiento();
        movimiento.setId(1L);
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipoMovimiento(TipoMovimiento.CREDITO);
        movimiento.setValor(new BigDecimal("500.00"));
        movimiento.setSaldo(new BigDecimal("2500.00"));
        movimiento.setNumeroCuenta("478758");
    }

    @Test
    void registrarMovimiento_Deposito_DeberiaRegistrarMovimientoExitosamente() {
        // Given
        when(cuentaService.obtenerCuentaPorNumero(anyString())).thenReturn(cuentaDTO);
        when(movimientoRepository.findByNumeroCuentaOrderByFechaDesc(anyString())).thenReturn(Arrays.asList());
        when(movimientoMapper.toEntity(any(MovimientoDTO.class))).thenReturn(movimiento);
        when(movimientoRepository.save(any(Movimiento.class))).thenReturn(movimiento);
        when(movimientoMapper.toDTO(any(Movimiento.class))).thenReturn(movimientoDTO);

        // When
        MovimientoDTO resultado = movimientoService.registrarMovimiento(movimientoDTO);

        // Then
        assertNotNull(resultado);
        assertEquals(TipoMovimiento.CREDITO, resultado.getTipoMovimiento());
        assertEquals(new BigDecimal("500.00"), resultado.getValor());
        verify(movimientoRepository).save(any(Movimiento.class));
    }

    @Test
    void registrarMovimiento_RetiroConSaldoSuficiente_DeberiaRegistrarMovimientoExitosamente() {
        // Given
        movimientoDTO.setTipoMovimiento(TipoMovimiento.DEBITO);
        movimientoDTO.setValor(new BigDecimal("100.00"));
        
        when(cuentaService.obtenerCuentaPorNumero(anyString())).thenReturn(cuentaDTO);
        when(movimientoRepository.findByNumeroCuentaOrderByFechaDesc(anyString())).thenReturn(Arrays.asList());
        when(movimientoMapper.toEntity(any(MovimientoDTO.class))).thenReturn(movimiento);
        when(movimientoRepository.save(any(Movimiento.class))).thenReturn(movimiento);
        when(movimientoMapper.toDTO(any(Movimiento.class))).thenReturn(movimientoDTO);

        // When
        MovimientoDTO resultado = movimientoService.registrarMovimiento(movimientoDTO);

        // Then
        assertNotNull(resultado);
        assertEquals(TipoMovimiento.DEBITO, resultado.getTipoMovimiento());
        verify(movimientoRepository).save(any(Movimiento.class));
    }

    @Test
    void registrarMovimiento_RetiroConSaldoInsuficiente_DeberiaLanzarExcepcion() {
        // Given
        movimientoDTO.setTipoMovimiento(TipoMovimiento.DEBITO);
        movimientoDTO.setValor(new BigDecimal("3000.00")); // Mayor al saldo inicial
        
        when(cuentaService.obtenerCuentaPorNumero(anyString())).thenReturn(cuentaDTO);
        when(movimientoRepository.findByNumeroCuentaOrderByFechaDesc(anyString())).thenReturn(Arrays.asList());

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            movimientoService.registrarMovimiento(movimientoDTO);
        });

        assertEquals("Saldo no disponible", exception.getMessage());
        verify(movimientoRepository, never()).save(any(Movimiento.class));
    }

    @Test
    void registrarMovimiento_CuentaInactiva_DeberiaLanzarExcepcion() {
        // Given
        cuentaDTO.setEstado(false);
        when(cuentaService.obtenerCuentaPorNumero(anyString())).thenReturn(cuentaDTO);

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            movimientoService.registrarMovimiento(movimientoDTO);
        });

        assertEquals("No se pueden realizar movimientos en una cuenta inactiva", exception.getMessage());
        verify(movimientoRepository, never()).save(any(Movimiento.class));
    }

    @Test
    void obtenerMovimientosPorCuenta_DeberiaRetornarListaDeMovimientos() {
        // Given
        List<Movimiento> movimientos = Arrays.asList(movimiento);
        List<MovimientoDTO> movimientosDTO = Arrays.asList(movimientoDTO);
        
        when(movimientoRepository.findByNumeroCuentaOrderByFechaDesc(anyString())).thenReturn(movimientos);
        when(movimientoMapper.toDTO(movimiento)).thenReturn(movimientoDTO);

        // When
        List<MovimientoDTO> resultado = movimientoService.obtenerMovimientosPorCuenta("478758");

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("478758", resultado.get(0).getNumeroCuenta());
    }

    @Test
    void obtenerSaldoActual_ConMovimientos_DeberiaRetornarSaldoCorrecto() {
        // Given
        movimiento.setSaldo(new BigDecimal("1500.00"));
        List<Movimiento> movimientos = Arrays.asList(movimiento);
        
        when(movimientoRepository.findByNumeroCuentaOrderByFechaDesc(anyString())).thenReturn(movimientos);

        // When
        BigDecimal saldo = movimientoService.obtenerSaldoActual("478758");

        // Then
        assertEquals(new BigDecimal("1500.00"), saldo);
    }

    @Test
    void obtenerSaldoActual_SinMovimientos_DeberiaRetornarSaldoInicial() {
        // Given
        when(movimientoRepository.findByNumeroCuentaOrderByFechaDesc(anyString())).thenReturn(Arrays.asList());
        when(cuentaService.obtenerCuentaPorNumero(anyString())).thenReturn(cuentaDTO);

        // When
        BigDecimal saldo = movimientoService.obtenerSaldoActual("478758");

        // Then
        assertEquals(new BigDecimal("2000.00"), saldo);
    }
}
