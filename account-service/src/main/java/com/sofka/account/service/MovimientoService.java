package com.sofka.account.service;

import com.sofka.account.dto.MovimientoDTO;
import com.sofka.account.dto.MovimientoRequestDTO;
import com.sofka.account.exception.ResourceNotFoundException;
import com.sofka.account.exception.BusinessException;
import com.sofka.account.mapper.MovimientoMapper;
import com.sofka.account.model.Cuenta;
import com.sofka.account.model.Movimiento;
import com.sofka.account.model.TipoMovimiento;
import com.sofka.account.repository.CuentaRepository;
import com.sofka.account.repository.MovimientoRepository;
import com.sofka.account.validation.MovimientoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final MovimientoMapper movimientoMapper;
    private final CuentaRepository cuentaRepository;
    private final MovimientoValidator movimientoValidator;
    private final CuentaService cuentaService;

    public MovimientoDTO registrarMovimiento(MovimientoRequestDTO movimientoRequestDTO) {
        // Validar que la cuenta existe
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimientoRequestDTO.getNumeroCuenta())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada: " + movimientoRequestDTO.getNumeroCuenta()));
        
        if (!cuenta.getEstado()) {
            throw new BusinessException("No se pueden realizar movimientos en una cuenta inactiva");
        }
        
        // Crear DTO completo para validación
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setNumeroCuenta(movimientoRequestDTO.getNumeroCuenta());
        movimientoDTO.setTipoMovimiento(movimientoRequestDTO.getTipoMovimiento());
        movimientoDTO.setValor(movimientoRequestDTO.getValor());
        
        // Validar datos del movimiento
        movimientoValidator.validarCreacionMovimiento(movimientoDTO, cuenta);

        // Calcular el nuevo saldo
        BigDecimal saldoActual = cuenta.getSaldoInicial();
        List<Movimiento> movimientosAnteriores = movimientoRepository.findByNumeroCuentaOrderByFechaDesc(cuenta.getNumeroCuenta());
        
        if (!movimientosAnteriores.isEmpty()) {
            saldoActual = movimientosAnteriores.get(0).getSaldo();
        }

        BigDecimal nuevoSaldo = calcularNuevoSaldo(saldoActual, movimientoRequestDTO.getValor(), movimientoRequestDTO.getTipoMovimiento());

        // Validar saldo suficiente para débitos
        if (movimientoRequestDTO.getTipoMovimiento() == TipoMovimiento.DEBITO && nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Saldo no disponible");
        }

        // Crear el movimiento
        movimientoDTO.setFecha(LocalDateTime.now());
        movimientoDTO.setSaldo(nuevoSaldo);

        Movimiento movimiento = movimientoMapper.toEntity(movimientoDTO);
        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);
        
        return movimientoMapper.toDTO(movimientoGuardado);
    }

    private BigDecimal calcularNuevoSaldo(BigDecimal saldoActual, BigDecimal valor, TipoMovimiento tipoMovimiento) {
        if (tipoMovimiento == TipoMovimiento.CREDITO) {
            return saldoActual.add(valor);
        } else if (tipoMovimiento == TipoMovimiento.DEBITO) {
            return saldoActual.subtract(valor);
        }
        return saldoActual;
    }

    @Transactional(readOnly = true)
    public List<MovimientoDTO> obtenerMovimientosPorCuenta(String numeroCuenta) {
        return movimientoRepository.findByNumeroCuentaOrderByFechaDesc(numeroCuenta)
                .stream()
                .map(movimientoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovimientoDTO> obtenerMovimientosPorRangoFechas(String numeroCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return movimientoRepository.findMovimientosPorRangoFechas(numeroCuenta, fechaInicio, fechaFin)
                .stream()
                .map(movimientoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MovimientoDTO obtenerMovimientoPorId(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado con ID: " + id));
        return movimientoMapper.toDTO(movimiento);
    }

    public void eliminarMovimiento(Long id) {
        if (!movimientoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movimiento no encontrado con ID: " + id);
        }
        movimientoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public BigDecimal obtenerSaldoActual(String numeroCuenta) {
        List<Movimiento> movimientos = movimientoRepository.findByNumeroCuentaOrderByFechaDesc(numeroCuenta);
        
        if (movimientos.isEmpty()) {
            var cuentaDTO = cuentaService.obtenerCuentaPorNumero(numeroCuenta);
            return cuentaDTO.getSaldoInicial();
        }
        
        return movimientos.get(0).getSaldo();
    }
}
