package com.xyz.CustomerMs.repositories;

import com.xyz.CustomerMs.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Buscar cliente por DNI
    Optional<Cliente> findByDni(String dni);
}
