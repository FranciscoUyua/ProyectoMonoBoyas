package com.monoboyas.api.sensores;

import com.monoboyas.api.operacion.Buque;
import com.monoboyas.api.operacion.Monoboya;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "sensores")
@Data
@NoArgsConstructor
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String unidad;

    @ManyToOne
    @JoinColumn(name = "monoboya_id")
    private Monoboya monoboya;          // null si el sensor es del buque

    @ManyToOne
    @JoinColumn(name = "buque_nro_imo")
    private Buque buque;               // null si el sensor es de la monoboya
}
