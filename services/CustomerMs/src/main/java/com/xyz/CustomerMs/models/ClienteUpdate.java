package com.xyz.CustomerMs.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteUpdate {

    String nombre;

    String apellido;

    @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe tener 8 dígitos")
    String dni;

    @Email(message = "El email debe ser válido")
    String email;
}
