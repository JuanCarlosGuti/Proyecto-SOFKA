package com.sofka.customer.repository;

import com.sofka.customer.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByClienteId(String clienteId);
    
    Optional<Cliente> findByIdentificacion(String identificacion);
    
    List<Cliente> findByEstado(Boolean estado);
    
    boolean existsByClienteId(String clienteId);
    
    boolean existsByIdentificacion(String identificacion);
}
