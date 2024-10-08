package com.xyz.CustomerMs.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String nombre;

    @Column(nullable = false)
    String apellido;

    @Column(nullable = false, unique = true)
    String dni;

    @Column(nullable = false, unique = true)
    String email;
}
