package com.sofka.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoCuentaDTO {

    private String clienteId;
    private String nombreCliente;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private List<MovimientoDTO> movimientos;
    private BigDecimal saldoDisponible;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
