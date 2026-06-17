package com.monoboyas.api.operacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "buques")
@Data
@NoArgsConstructor
public class Buque {

    @Id
    @Column(name = "nro_imo")
    private String nroIMO;

    @Column(nullable = false)
    private String nombre;

    private Double capacidad;
}
