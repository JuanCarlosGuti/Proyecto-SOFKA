package com.sofka.account.service;

import com.sofka.account.dto.CuentaDTO;
import com.sofka.account.exception.ResourceNotFoundException;
import com.sofka.account.exception.ResourceConflictException;
import com.sofka.account.mapper.CuentaMapper;
import com.sofka.account.model.Cuenta;
import com.sofka.account.repository.CuentaRepository;
import com.sofka.account.validation.CuentaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final CuentaMapper cuentaMapper;
    private final CuentaValidator cuentaValidator;

    public CuentaDTO crearCuenta(CuentaDTO cuentaDTO) {
        // Validar datos de la cuenta
        cuentaValidator.validarCreacionCuenta(cuentaDTO);
        
        if (cuentaRepository.existsByNumeroCuenta(cuentaDTO.getNumeroCuenta())) {
            throw new ResourceConflictException("Ya existe una cuenta con el número: " + cuentaDTO.getNumeroCuenta());
        }

        Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);
        return cuentaMapper.toDTO(cuentaGuardada);
    }

    @Transactional(readOnly = true)
    public List<CuentaDTO> obtenerTodasLasCuentas() {
        return cuentaRepository.findAll()
                .stream()
                .map(cuentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CuentaDTO> obtenerCuentasPorClienteId(String clienteId) {
        return cuentaRepository.findByClienteId(clienteId)
                .stream()
                .map(cuentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CuentaDTO> obtenerCuentasActivasPorClienteId(String clienteId) {
        return cuentaRepository.findCuentasActivasByClienteId(clienteId)
                .stream()
                .map(cuentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CuentaDTO obtenerCuentaPorId(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + id));
        return cuentaMapper.toDTO(cuenta);
    }

    @Transactional(readOnly = true)
    public CuentaDTO obtenerCuentaPorNumero(String numeroCuenta) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con número: " + numeroCuenta));
        return cuentaMapper.toDTO(cuenta);
    }

    public CuentaDTO actualizarCuenta(Long id, CuentaDTO cuentaDTO) {
        Cuenta cuentaExistente = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + id));

        // Verificar si el nuevo número de cuenta ya existe en otra cuenta
        if (!cuentaExistente.getNumeroCuenta().equals(cuentaDTO.getNumeroCuenta()) &&
            cuentaRepository.existsByNumeroCuenta(cuentaDTO.getNumeroCuenta())) {
            throw new ResourceConflictException("Ya existe una cuenta con el número: " + cuentaDTO.getNumeroCuenta());
        }

        cuentaMapper.updateEntity(cuentaDTO, cuentaExistente);
        Cuenta cuentaActualizada = cuentaRepository.save(cuentaExistente);
        return cuentaMapper.toDTO(cuentaActualizada);
    }

    public void eliminarCuenta(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cuenta no encontrada con ID: " + id);
        }
        cuentaRepository.deleteById(id);
    }

    public CuentaDTO cambiarEstadoCuenta(Long id, Boolean estado) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + id));
        
        cuenta.setEstado(estado);
        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);
        return cuentaMapper.toDTO(cuentaActualizada);
    }
}
