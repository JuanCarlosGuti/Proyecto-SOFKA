package com.sofka.account.validation;

import com.sofka.account.dto.MovimientoDTO;
import com.sofka.account.exception.BusinessException;
import com.sofka.account.model.Cuenta;
import com.sofka.account.model.TipoMovimiento;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class MovimientoValidator {
    
    private static final BigDecimal VALOR_MINIMO = BigDecimal.valueOf(1);
    private static final BigDecimal VALOR_MAXIMO = BigDecimal.valueOf(100000);
    private static final BigDecimal LIMITE_DIARIO_DEBITO = BigDecimal.valueOf(1000);
    
    public void validarCreacionMovimiento(MovimientoDTO movimientoDTO, Cuenta cuenta) {
        validarValor(movimientoDTO.getValor());
        validarTipoMovimiento(movimientoDTO.getTipoMovimiento());
        validarSaldoSuficiente(movimientoDTO, cuenta);
        validarLimiteDiario(movimientoDTO, cuenta);
    }
    
    public void validarValor(BigDecimal valor) {
        if (valor == null) {
            throw new BusinessException("El valor del movimiento no puede ser nulo");
        }
        
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El valor del movimiento debe ser mayor a cero");
        }
        
        if (valor.compareTo(VALOR_MINIMO) < 0) {
            throw new BusinessException("El valor mínimo para un movimiento es $1");
        }
        
        if (valor.compareTo(VALOR_MAXIMO) > 0) {
            throw new BusinessException("El valor máximo para un movimiento es $100,000");
        }
    }
    
    public void validarTipoMovimiento(TipoMovimiento tipoMovimiento) {
        if (tipoMovimiento == null) {
            throw new BusinessException("El tipo de movimiento es obligatorio");
        }
    }
    
    public void validarSaldoSuficiente(MovimientoDTO movimientoDTO, Cuenta cuenta) {
        if (movimientoDTO.getTipoMovimiento() == TipoMovimiento.DEBITO) {
            BigDecimal saldoActual = obtenerSaldoActual(cuenta);
            BigDecimal saldoDespuesMovimiento = saldoActual.subtract(movimientoDTO.getValor());
            
            if (saldoDespuesMovimiento.compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("Saldo insuficiente. Saldo actual: $" + saldoActual);
            }
        }
    }
    
    public void validarLimiteDiario(MovimientoDTO movimientoDTO, Cuenta cuenta) {
        if (movimientoDTO.getTipoMovimiento() == TipoMovimiento.DEBITO) {
            // En una implementación real, aquí se consultaría la base de datos
            // para obtener el total de débitos del día
            // Por simplicidad, asumimos que no hay límite diario excedido
            // TODO: Implementar consulta de movimientos del día
        }
    }
    
    private BigDecimal obtenerSaldoActual(Cuenta cuenta) {
        // En una implementación real, aquí se consultaría el último movimiento
        // Por simplicidad, retornamos el saldo inicial
        return cuenta.getSaldoInicial();
    }
    
    public void validarFechaMovimiento(LocalDateTime fecha) {
        if (fecha == null) {
            throw new BusinessException("La fecha del movimiento es obligatoria");
        }
        
        LocalDateTime ahora = LocalDateTime.now();
        if (fecha.isAfter(ahora)) {
            throw new BusinessException("No se pueden crear movimientos con fecha futura");
        }
        
        // No permitir movimientos de más de 30 días atrás
        LocalDateTime fechaLimite = ahora.minusDays(30);
        if (fecha.isBefore(fechaLimite)) {
            throw new BusinessException("No se pueden crear movimientos de más de 30 días atrás");
        }
    }
}

