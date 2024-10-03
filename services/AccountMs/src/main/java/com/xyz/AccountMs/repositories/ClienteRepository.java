package com.xyz.AccountMs.repositories;

import com.xyz.AccountMs.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
