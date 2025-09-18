package com.sofka.account.service;

import com.sofka.account.dto.EstadoCuentaReporteDTO;
import com.sofka.account.dto.MovimientoReporteDTO;
import com.sofka.account.dto.MovimientosPorFechaDTO;
import com.sofka.account.exception.ResourceNotFoundException;
import com.sofka.account.mapper.ReporteMapper;
import com.sofka.account.model.Cuenta;
import com.sofka.account.model.Movimiento;
import com.sofka.account.model.TipoMovimiento;
import com.sofka.account.repository.CuentaRepository;
import com.sofka.account.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReporteService {
    
    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final ReporteMapper reporteMapper;
    
    public EstadoCuentaReporteDTO generarEstadoCuenta(String numeroCuenta, LocalDate fechaInicio, LocalDate fechaFin) {
        log.info("Generando estado de cuenta para cuenta: {} desde {} hasta {}", numeroCuenta, fechaInicio, fechaFin);
        
        // Buscar la cuenta
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada: " + numeroCuenta));
        
        // Obtener movimientos en el rango de fechas
        List<Movimiento> movimientos = movimientoRepository.findByNumeroCuentaAndFechaBetween(
                cuenta.getNumeroCuenta(), fechaInicio.atStartOfDay(), fechaFin.atTime(23, 59, 59));
        
        // Convertir a DTOs
        List<MovimientoReporteDTO> movimientosDTO = reporteMapper.toMovimientoReporteDTOList(movimientos);
        
        // Calcular totales
        BigDecimal totalDebitos = movimientos.stream()
                .filter(m -> m.getTipoMovimiento() == TipoMovimiento.DEBITO)
                .map(Movimiento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalCreditos = movimientos.stream()
                .filter(m -> m.getTipoMovimiento() == TipoMovimiento.CREDITO)
                .map(Movimiento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Obtener saldo actual (último movimiento)
        BigDecimal saldoDisponible = movimientos.isEmpty() ? 
                cuenta.getSaldoInicial() : 
                movimientos.get(movimientos.size() - 1).getSaldo();
        
        // Construir reporte
        EstadoCuentaReporteDTO reporte = new EstadoCuentaReporteDTO();
        reporte.setClienteId(cuenta.getClienteId());
        reporte.setNumeroCuenta(cuenta.getNumeroCuenta());
        reporte.setTipoCuenta(cuenta.getTipoCuenta().name());
        reporte.setSaldoInicial(cuenta.getSaldoInicial());
        reporte.setSaldoDisponible(saldoDisponible);
        reporte.setFechaInicio(fechaInicio);
        reporte.setFechaFin(fechaFin);
        reporte.setFechaGeneracion(LocalDateTime.now());
        reporte.setMovimientos(movimientosDTO);
        reporte.setTotalDebitos(totalDebitos);
        reporte.setTotalCreditos(totalCreditos);
        reporte.setTotalMovimientos(movimientos.size());
        
        log.info("Estado de cuenta generado exitosamente. Total movimientos: {}", movimientos.size());
        return reporte;
    }
    
    public MovimientosPorFechaDTO obtenerMovimientosPorFecha(String numeroCuenta, LocalDate fechaInicio, LocalDate fechaFin, String tipoFiltro) {
        log.info("Obteniendo movimientos para cuenta: {} desde {} hasta {} filtro: {}", 
                numeroCuenta, fechaInicio, fechaFin, tipoFiltro);
        
        // Buscar la cuenta
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada: " + numeroCuenta));
        
        // Obtener movimientos en el rango de fechas
        List<Movimiento> movimientos = movimientoRepository.findByNumeroCuentaAndFechaBetween(
                cuenta.getNumeroCuenta(), fechaInicio.atStartOfDay(), fechaFin.atTime(23, 59, 59));
        
        // Aplicar filtro por tipo si es necesario
        if (!"TODOS".equals(tipoFiltro)) {
            TipoMovimiento tipo = "DEBITOS".equals(tipoFiltro) ? TipoMovimiento.DEBITO : TipoMovimiento.CREDITO;
            movimientos = movimientos.stream()
                    .filter(m -> m.getTipoMovimiento() == tipo)
                    .collect(Collectors.toList());
        }
        
        // Convertir a DTOs
        List<MovimientoReporteDTO> movimientosDTO = reporteMapper.toMovimientoReporteDTOList(movimientos);
        
        // Construir respuesta
        MovimientosPorFechaDTO resultado = new MovimientosPorFechaDTO();
        resultado.setClienteId(cuenta.getClienteId());
        resultado.setNumeroCuenta(cuenta.getNumeroCuenta());
        resultado.setFechaInicio(fechaInicio);
        resultado.setFechaFin(fechaFin);
        resultado.setMovimientos(movimientosDTO);
        resultado.setTotalMovimientos(movimientos.size());
        resultado.setTipoFiltro(tipoFiltro);
        
        log.info("Movimientos obtenidos exitosamente. Total: {}", movimientos.size());
        return resultado;
    }
    
    public List<MovimientosPorFechaDTO> obtenerMovimientosPorCliente(String clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        log.info("Obteniendo movimientos para cliente: {} desde {} hasta {}", clienteId, fechaInicio, fechaFin);
        
        // Buscar todas las cuentas del cliente
        List<Cuenta> cuentas = cuentaRepository.findByClienteIdAndEstado(clienteId, true);
        
        if (cuentas.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron cuentas activas para el cliente: " + clienteId);
        }
        
        // Obtener movimientos para cada cuenta
        return cuentas.stream()
                .map(cuenta -> {
                    List<Movimiento> movimientos = movimientoRepository.findByNumeroCuentaAndFechaBetween(
                            cuenta.getNumeroCuenta(), fechaInicio.atStartOfDay(), fechaFin.atTime(23, 59, 59));
                    
                    List<MovimientoReporteDTO> movimientosDTO = reporteMapper.toMovimientoReporteDTOList(movimientos);
                    
                    MovimientosPorFechaDTO resultado = new MovimientosPorFechaDTO();
                    resultado.setClienteId(clienteId);
                    resultado.setNumeroCuenta(cuenta.getNumeroCuenta());
                    resultado.setFechaInicio(fechaInicio);
                    resultado.setFechaFin(fechaFin);
                    resultado.setMovimientos(movimientosDTO);
                    resultado.setTotalMovimientos(movimientos.size());
                    resultado.setTipoFiltro("TODOS");
                    
                    return resultado;
                })
                .collect(Collectors.toList());
    }
}

