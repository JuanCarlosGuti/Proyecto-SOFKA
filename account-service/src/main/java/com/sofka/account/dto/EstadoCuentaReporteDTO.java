package com.sofka.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoCuentaReporteDTO {
    private String clienteId;
    private String nombreCliente;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private BigDecimal saldoDisponible;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDateTime fechaGeneracion;
    private List<MovimientoReporteDTO> movimientos;
    private BigDecimal totalDebitos;
    private BigDecimal totalCreditos;
    private int totalMovimientos;
}

