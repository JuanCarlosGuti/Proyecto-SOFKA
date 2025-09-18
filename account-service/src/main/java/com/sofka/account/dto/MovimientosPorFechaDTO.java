package com.sofka.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientosPorFechaDTO {
    private String clienteId;
    private String numeroCuenta;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<MovimientoReporteDTO> movimientos;
    private int totalMovimientos;
    private String tipoFiltro; // "TODOS", "DEBITOS", "CREDITOS"
}

