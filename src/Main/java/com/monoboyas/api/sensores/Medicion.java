package com.monoboyas.api.sensores;

import com.monoboyas.api.operacion.Operacion;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mediciones",
    indexes = {
        @Index(name = "idx_medicion_sensor_timestamp", columnList = "sensor_id, timestamp"),
        @Index(name = "idx_medicion_operacion", columnList = "operacion_id")
    })
@Data
@NoArgsConstructor
public class Medicion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private String unidad;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "operacion_id")
    private Operacion operacion;
}
