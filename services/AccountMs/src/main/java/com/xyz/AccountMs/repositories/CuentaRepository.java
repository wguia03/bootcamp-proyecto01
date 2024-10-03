package com.xyz.AccountMs.repositories;

import com.xyz.AccountMs.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
}
