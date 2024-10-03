package com.xyz.AccountMs.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String numeroCuenta;

    @Column(nullable = false)
    private Double saldo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCuentaEnum tipoCuenta;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
