package com.xyz.AccountMs.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaResponse {

    private Integer id;
    private String numeroCuenta;
    private Double saldo;
    private TipoCuentaEnum tipoCuenta;
    private Integer clienteId;
}
