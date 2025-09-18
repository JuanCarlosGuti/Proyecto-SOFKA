package com.sofka.account.repository;

import com.sofka.account.model.Movimiento;
import com.sofka.account.model.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    
    List<Movimiento> findByNumeroCuenta(String numeroCuenta);
    
    List<Movimiento> findByNumeroCuentaOrderByFechaDesc(String numeroCuenta);
    
    List<Movimiento> findByTipoMovimiento(TipoMovimiento tipoMovimiento);
    
    @Query("SELECT m FROM Movimiento m WHERE m.numeroCuenta = :numeroCuenta AND m.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY m.fecha DESC")
    List<Movimiento> findMovimientosPorRangoFechas(
            @Param("numeroCuenta") String numeroCuenta,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT m FROM Movimiento m WHERE m.numeroCuenta = :numeroCuenta AND m.fecha >= :fechaInicio ORDER BY m.fecha DESC")
    List<Movimiento> findMovimientosDesdeFecha(
            @Param("numeroCuenta") String numeroCuenta,
            @Param("fechaInicio") LocalDateTime fechaInicio);
    
    @Query("SELECT m FROM Movimiento m WHERE m.numeroCuenta = :numeroCuenta AND m.fecha <= :fechaFin ORDER BY m.fecha DESC")
    List<Movimiento> findMovimientosHastaFecha(
            @Param("numeroCuenta") String numeroCuenta,
            @Param("fechaFin") LocalDateTime fechaFin);
    
    // Métodos para trabajar con numeroCuenta (que es lo que realmente tiene la entidad)
    @Query("SELECT m FROM Movimiento m WHERE m.numeroCuenta = :numeroCuenta AND m.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY m.fecha DESC")
    List<Movimiento> findByNumeroCuentaAndFechaBetween(
            @Param("numeroCuenta") String numeroCuenta,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);
}
