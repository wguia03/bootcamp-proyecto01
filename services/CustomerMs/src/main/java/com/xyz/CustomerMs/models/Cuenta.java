package com.xyz.CustomerMs.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cuentas", schema = "banco")
public class Cuenta {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "numero_cuenta", nullable = false)
    private String numeroCuenta;

    @NotNull
    @Column(name = "saldo", nullable = false)
    private Double saldo;

    @NotNull
    @Lob
    @Column(name = "tipo_cuenta", nullable = false)
    private String tipoCuenta;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

}