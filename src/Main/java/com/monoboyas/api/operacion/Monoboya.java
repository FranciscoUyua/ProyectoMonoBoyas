package com.monoboyas.api.operacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "monoboyas")
@Data
@NoArgsConstructor
public class Monoboya {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMonoboya estado;

    @ManyToOne
    @JoinColumn(name = "planta_id", nullable = false)
    private Planta planta;

    @OneToOne
    @JoinColumn(name = "operacion_activa_id")
    private Operacion operacionActiva;     // null si no hay operación en curso

}
