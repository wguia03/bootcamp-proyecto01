package com.xyz.CustomerMs.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponse {

    @NotNull
    Integer id;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotEmpty(message = "El nombre no puede estar vacío")
    String nombre;

    @NotNull(message = "El apellido no puede ser nulo")
    @NotEmpty(message = "El apellido no puede estar vacío")
    String apellido;

    @NotNull(message = "El DNI no puede ser nulo")
    @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe tener 8 dígitos")
    String dni;

    @NotNull(message = "El email no puede ser nulo")
    @Email(message = "El email debe ser válido")
    String email;
}
