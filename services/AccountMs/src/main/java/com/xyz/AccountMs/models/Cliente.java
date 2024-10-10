package com.xyz.AccountMs.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "clientes", schema = "banco")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Size(max = 255)
    @NotNull
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Size(max = 255)
    @NotNull
    @Column(name = "dni", nullable = false)
    private String dni;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;
}