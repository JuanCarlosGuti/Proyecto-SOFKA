package com.sofka.account.controller;

import com.sofka.account.dto.EstadoCuentaReporteDTO;
import com.sofka.account.dto.MovimientosPorFechaDTO;
import com.sofka.account.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "API para generar reportes de cuentas y movimientos")
public class ReporteController {
    
    private final ReporteService reporteService;
    
    @GetMapping("/estado-cuenta/{numeroCuenta}")
    @Operation(summary = "Generar estado de cuenta", 
               description = "Genera un reporte completo del estado de cuenta para un rango de fechas")
    public ResponseEntity<EstadoCuentaReporteDTO> generarEstadoCuenta(
            @Parameter(description = "Número de cuenta") @PathVariable String numeroCuenta,
            @Parameter(description = "Fecha de inicio (yyyy-MM-dd)") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @Parameter(description = "Fecha de fin (yyyy-MM-dd)") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        EstadoCuentaReporteDTO reporte = reporteService.generarEstadoCuenta(numeroCuenta, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
    
    @GetMapping("/movimientos/{numeroCuenta}")
    @Operation(summary = "Obtener movimientos por fecha", 
               description = "Obtiene los movimientos de una cuenta en un rango de fechas con filtro opcional")
    public ResponseEntity<MovimientosPorFechaDTO> obtenerMovimientosPorFecha(
            @Parameter(description = "Número de cuenta") @PathVariable String numeroCuenta,
            @Parameter(description = "Fecha de inicio (yyyy-MM-dd)") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @Parameter(description = "Fecha de fin (yyyy-MM-dd)") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @Parameter(description = "Tipo de filtro: TODOS, DEBITOS, CREDITOS") 
            @RequestParam(defaultValue = "TODOS") String tipoFiltro) {
        
        MovimientosPorFechaDTO movimientos = reporteService.obtenerMovimientosPorFecha(
                numeroCuenta, fechaInicio, fechaFin, tipoFiltro);
        return ResponseEntity.ok(movimientos);
    }
    
    @GetMapping("/movimientos/cliente/{clienteId}")
    @Operation(summary = "Obtener movimientos por cliente", 
               description = "Obtiene los movimientos de todas las cuentas de un cliente en un rango de fechas")
    public ResponseEntity<List<MovimientosPorFechaDTO>> obtenerMovimientosPorCliente(
            @Parameter(description = "ID del cliente") @PathVariable String clienteId,
            @Parameter(description = "Fecha de inicio (yyyy-MM-dd)") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @Parameter(description = "Fecha de fin (yyyy-MM-dd)") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        List<MovimientosPorFechaDTO> movimientos = reporteService.obtenerMovimientosPorCliente(
                clienteId, fechaInicio, fechaFin);
        return ResponseEntity.ok(movimientos);
    }
}

