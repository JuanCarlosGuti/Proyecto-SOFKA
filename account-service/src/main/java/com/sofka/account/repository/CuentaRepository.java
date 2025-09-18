package com.sofka.account.repository;

import com.sofka.account.model.Cuenta;
import com.sofka.account.model.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
    
    List<Cuenta> findByClienteId(String clienteId);
    
    List<Cuenta> findByClienteIdAndEstado(String clienteId, Boolean estado);
    
    List<Cuenta> findByTipoCuenta(TipoCuenta tipoCuenta);
    
    List<Cuenta> findByEstado(Boolean estado);
    
    boolean existsByNumeroCuenta(String numeroCuenta);
    
    @Query("SELECT c FROM Cuenta c WHERE c.clienteId = :clienteId AND c.estado = true")
    List<Cuenta> findCuentasActivasByClienteId(@Param("clienteId") String clienteId);
}
