package com.monoboyas.api.alertas;

import com.monoboyas.api.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_alerta")
@Data
@NoArgsConstructor
public class UsuarioAlerta {

    @EmbeddedId
    private UsuarioAlertaId id;

    @ManyToOne
    @MapsId("alertaId")
    @JoinColumn(name = "alerta_id")
    private Alerta alerta;

    @ManyToOne
    @MapsId("usuarioDni")
    @JoinColumn(name = "usuario_dni")
    private Usuario usuario;

    @CreationTimestamp
    private LocalDateTime fechaRecepcion;

    private Boolean reconocida = false;         // solo aplica a tipo ROJO

    private LocalDateTime fechaReconocimiento;  // null hasta que se reconozca
}
