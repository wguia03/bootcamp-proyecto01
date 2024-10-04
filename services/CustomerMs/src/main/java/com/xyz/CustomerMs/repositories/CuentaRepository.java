package com.xyz.CustomerMs.repositories;

import com.xyz.CustomerMs.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
    @Query("SELECT COUNT(c) > 0 FROM Cuenta c WHERE c.cliente.id = :clienteId")
    boolean existsByClienteId(@Param("clienteId") Integer clienteId);
}
