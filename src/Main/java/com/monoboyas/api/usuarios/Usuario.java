package com.monoboyas.api.usuarios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "rol", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
public abstract class Usuario {

    @Id
    @Column(name = "dni")
    private String dni;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String contrasena;
}
