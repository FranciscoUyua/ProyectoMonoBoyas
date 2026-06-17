package com.monoboyas.api.alertas;

import com.monoboyas.api.operacion.Operacion;
import com.monoboyas.api.sensores.Medicion;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "alertas")
@Data
@NoArgsConstructor
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAlerta tipo;

    @Column(nullable = false)
    private String mensaje;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "operacion_id", nullable = false)
    private Operacion operacion;

    @ManyToOne
    @JoinColumn(name = "medicion_id", nullable = false)
    private Medicion medicion;
}
