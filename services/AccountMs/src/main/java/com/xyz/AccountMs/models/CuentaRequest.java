package com.xyz.AccountMs.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CuentaRequest {
    @NotNull(message = "El saldo de la cuenta no puede ser nulo")
    @Positive(message = "El saldo inicial de la cuenta debe ser mayor a 0")
    private Double saldo;

    @NotNull(message = "El tipo de cuenta no puede ser nulo")
    private TipoCuentaEnum tipoCuenta;

    @NotNull(message = "El cliente no puede ser nulo")
    private Integer clienteId;
}
