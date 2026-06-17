package com.monoboyas.api.operacion;

import com.monoboyas.api.usuarios.OperadorBuque;
import com.monoboyas.api.usuarios.OperadorLancha;
import com.monoboyas.api.usuarios.OperadorPlanta;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "operaciones")
@Data
@NoArgsConstructor
public class Operacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoOperacion estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoOperacion tipo;

    @ManyToOne
    @JoinColumn(name = "monoboya_id")
    private Monoboya monoboya;              // nullable hasta PREPARADA

    @ManyToOne
    @JoinColumn(name = "buque_nro_imo", nullable = false)
    private Buque buque;

    @ManyToOne
    @JoinColumn(name = "planta_id", nullable = false)
    private Planta planta;

    @ManyToOne
    @JoinColumn(name = "operador_buque_dni")
    private OperadorBuque operadorBuque;    // asignado en PLANIFICADA

    @ManyToOne
    @JoinColumn(name = "operador_planta_dni")
    private OperadorPlanta operadorPlanta;  // nullable hasta PREPARADA

    @ManyToOne
    @JoinColumn(name = "operador_lancha_dni")
    private OperadorLancha operadorLancha;  // nullable hasta PREPARADA
}
