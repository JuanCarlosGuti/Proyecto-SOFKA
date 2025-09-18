package com.sofka.account.controller;

import com.sofka.account.dto.MovimientoDTO;
import com.sofka.account.dto.MovimientoRequestDTO;
import com.sofka.account.service.MovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
@Tag(name = "Movimientos", description = "API para gestión de movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping
    @Operation(summary = "Registrar un nuevo movimiento", description = "Registra un nuevo movimiento en una cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimiento registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o saldo insuficiente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<MovimientoDTO> registrarMovimiento(@Valid @RequestBody MovimientoRequestDTO movimientoRequestDTO) {
        MovimientoDTO movimientoRegistrado = movimientoService.registrarMovimiento(movimientoRequestDTO);
        return new ResponseEntity<>(movimientoRegistrado, HttpStatus.CREATED);
    }

    @GetMapping("/cuenta/{numeroCuenta}")
    @Operation(summary = "Obtener movimientos por cuenta", description = "Retorna todos los movimientos de una cuenta específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida exitosamente")
    })
    public ResponseEntity<List<MovimientoDTO>> obtenerMovimientosPorCuenta(
            @Parameter(description = "Número de la cuenta") @PathVariable String numeroCuenta) {
        List<MovimientoDTO> movimientos = movimientoService.obtenerMovimientosPorCuenta(numeroCuenta);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/cuenta/{numeroCuenta}/rango")
    @Operation(summary = "Obtener movimientos por rango de fechas", description = "Retorna los movimientos de una cuenta en un rango de fechas específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida exitosamente")
    })
    public ResponseEntity<List<MovimientoDTO>> obtenerMovimientosPorRangoFechas(
            @Parameter(description = "Número de la cuenta") @PathVariable String numeroCuenta,
            @Parameter(description = "Fecha de inicio") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @Parameter(description = "Fecha de fin") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<MovimientoDTO> movimientos = movimientoService.obtenerMovimientosPorRangoFechas(numeroCuenta, fechaInicio, fechaFin);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener movimiento por ID", description = "Retorna un movimiento específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    public ResponseEntity<MovimientoDTO> obtenerMovimientoPorId(
            @Parameter(description = "ID del movimiento") @PathVariable Long id) {
        MovimientoDTO movimiento = movimientoService.obtenerMovimientoPorId(id);
        return ResponseEntity.ok(movimiento);
    }

    @GetMapping("/cuenta/{numeroCuenta}/saldo")
    @Operation(summary = "Obtener saldo actual de la cuenta", description = "Retorna el saldo actual de una cuenta específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<BigDecimal> obtenerSaldoActual(
            @Parameter(description = "Número de la cuenta") @PathVariable String numeroCuenta) {
        BigDecimal saldo = movimientoService.obtenerSaldoActual(numeroCuenta);
        return ResponseEntity.ok(saldo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar movimiento", description = "Elimina un movimiento del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movimiento eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    public ResponseEntity<Void> eliminarMovimiento(
            @Parameter(description = "ID del movimiento") @PathVariable Long id) {
        movimientoService.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}
