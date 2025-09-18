package com.sofka.account.validation;

import com.sofka.account.dto.CuentaDTO;
import com.sofka.account.exception.BusinessException;
import com.sofka.account.model.TipoCuenta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CuentaValidator {
    
    private static final BigDecimal SALDO_MINIMO_AHORRO = BigDecimal.valueOf(0);
    private static final BigDecimal SALDO_MINIMO_CORRIENTE = BigDecimal.valueOf(1000);
    private static final BigDecimal SALDO_MAXIMO_INICIAL = BigDecimal.valueOf(1000000);
    
    public void validarCreacionCuenta(CuentaDTO cuentaDTO) {
        validarSaldoInicial(cuentaDTO.getSaldoInicial(), cuentaDTO.getTipoCuenta());
        validarNumeroCuenta(cuentaDTO.getNumeroCuenta());
        validarClienteId(cuentaDTO.getClienteId());
    }
    
    public void validarSaldoInicial(BigDecimal saldoInicial, TipoCuenta tipoCuenta) {
        if (saldoInicial == null) {
            throw new BusinessException("El saldo inicial no puede ser nulo");
        }
        
        if (saldoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("El saldo inicial no puede ser negativo");
        }
        
        if (saldoInicial.compareTo(SALDO_MAXIMO_INICIAL) > 0) {
            throw new BusinessException("El saldo inicial no puede exceder $1,000,000");
        }
        
        BigDecimal saldoMinimo = tipoCuenta == TipoCuenta.AHORRO ? 
                SALDO_MINIMO_AHORRO : SALDO_MINIMO_CORRIENTE;
        
        if (saldoInicial.compareTo(saldoMinimo) < 0) {
            String mensaje = tipoCuenta == TipoCuenta.AHORRO ? 
                    "El saldo inicial para cuenta de ahorros debe ser mayor o igual a $0" :
                    "El saldo inicial para cuenta corriente debe ser mayor o igual a $1,000";
            throw new BusinessException(mensaje);
        }
    }
    
    public void validarNumeroCuenta(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
            throw new BusinessException("El número de cuenta es obligatorio");
        }
        
        if (numeroCuenta.length() < 6 || numeroCuenta.length() > 20) {
            throw new BusinessException("El número de cuenta debe tener entre 6 y 20 caracteres");
        }
        
        if (!numeroCuenta.matches("^[0-9]+$")) {
            throw new BusinessException("El número de cuenta solo puede contener dígitos");
        }
    }
    
    public void validarClienteId(String clienteId) {
        if (clienteId == null || clienteId.trim().isEmpty()) {
            throw new BusinessException("El ID del cliente es obligatorio");
        }
        
        if (clienteId.length() < 3 || clienteId.length() > 20) {
            throw new BusinessException("El ID del cliente debe tener entre 3 y 20 caracteres");
        }
        
        if (!clienteId.matches("^[A-Z0-9]+$")) {
            throw new BusinessException("El ID del cliente solo puede contener letras mayúsculas y números");
        }
    }
}

