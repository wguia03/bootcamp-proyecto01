package com.xyz.TransactionMs.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferenciaRequest {

    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser mayor a 0")
    Double monto;

    @NotNull(message = "El monto no puede ser nulo")
    @Pattern(regexp = "^[0-9]{16}$", message = "El número de cuenta debe tener 16 dígitos")
    String cuenta_origen;

    @NotNull(message = "El monto no puede ser nulo")
    @Pattern(regexp = "^[0-9]{16}$", message = "El número de cuenta debe tener 16 dígitos")
    String cuenta_destino;
}
