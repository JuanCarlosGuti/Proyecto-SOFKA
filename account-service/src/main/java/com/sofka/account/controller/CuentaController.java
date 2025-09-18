package com.sofka.account.controller;

import com.sofka.account.dto.CuentaDTO;
import com.sofka.account.service.CuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
@Tag(name = "Cuentas", description = "API para gestión de cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping
    @Operation(summary = "Crear una nueva cuenta", description = "Crea una nueva cuenta en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflicto: número de cuenta ya existe")
    })
    public ResponseEntity<CuentaDTO> crearCuenta(@Valid @RequestBody CuentaDTO cuentaDTO) {
        CuentaDTO cuentaCreada = cuentaService.crearCuenta(cuentaDTO);
        return new ResponseEntity<>(cuentaCreada, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las cuentas", description = "Retorna una lista de todas las cuentas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida exitosamente")
    })
    public ResponseEntity<List<CuentaDTO>> obtenerTodasLasCuentas() {
        List<CuentaDTO> cuentas = cuentaService.obtenerTodasLasCuentas();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Obtener cuentas por cliente", description = "Retorna las cuentas de un cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cuentas del cliente obtenida exitosamente")
    })
    public ResponseEntity<List<CuentaDTO>> obtenerCuentasPorCliente(
            @Parameter(description = "ID del cliente") @PathVariable String clienteId) {
        List<CuentaDTO> cuentas = cuentaService.obtenerCuentasPorClienteId(clienteId);
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/cliente/{clienteId}/activas")
    @Operation(summary = "Obtener cuentas activas por cliente", description = "Retorna las cuentas activas de un cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cuentas activas del cliente obtenida exitosamente")
    })
    public ResponseEntity<List<CuentaDTO>> obtenerCuentasActivasPorCliente(
            @Parameter(description = "ID del cliente") @PathVariable String clienteId) {
        List<CuentaDTO> cuentas = cuentaService.obtenerCuentasActivasPorClienteId(clienteId);
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cuenta por ID", description = "Retorna una cuenta específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<CuentaDTO> obtenerCuentaPorId(
            @Parameter(description = "ID de la cuenta") @PathVariable Long id) {
        CuentaDTO cuenta = cuentaService.obtenerCuentaPorId(id);
        return ResponseEntity.ok(cuenta);
    }

    @GetMapping("/numero/{numeroCuenta}")
    @Operation(summary = "Obtener cuenta por número", description = "Retorna una cuenta específica por su número")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<CuentaDTO> obtenerCuentaPorNumero(
            @Parameter(description = "Número de la cuenta") @PathVariable String numeroCuenta) {
        CuentaDTO cuenta = cuentaService.obtenerCuentaPorNumero(numeroCuenta);
        return ResponseEntity.ok(cuenta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cuenta", description = "Actualiza los datos de una cuenta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto: número de cuenta ya existe")
    })
    public ResponseEntity<CuentaDTO> actualizarCuenta(
            @Parameter(description = "ID de la cuenta") @PathVariable Long id,
            @Valid @RequestBody CuentaDTO cuentaDTO) {
        CuentaDTO cuentaActualizada = cuentaService.actualizarCuenta(id, cuentaDTO);
        return ResponseEntity.ok(cuentaActualizada);
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de la cuenta", description = "Cambia el estado activo/inactivo de una cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado de la cuenta actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<CuentaDTO> cambiarEstadoCuenta(
            @Parameter(description = "ID de la cuenta") @PathVariable Long id,
            @Parameter(description = "Nuevo estado de la cuenta") @RequestParam Boolean estado) {
        CuentaDTO cuentaActualizada = cuentaService.cambiarEstadoCuenta(id, estado);
        return ResponseEntity.ok(cuentaActualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cuenta", description = "Elimina una cuenta del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuenta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<Void> eliminarCuenta(
            @Parameter(description = "ID de la cuenta") @PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }
}
