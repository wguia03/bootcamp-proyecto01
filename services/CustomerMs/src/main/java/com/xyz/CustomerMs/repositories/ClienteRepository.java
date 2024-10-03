package com.xyz.CustomerMs.repositories;

import com.xyz.CustomerMs.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
