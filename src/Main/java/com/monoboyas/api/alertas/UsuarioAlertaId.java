package com.monoboyas.api.alertas;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioAlertaId implements Serializable {
    private UUID alertaId;
    private String usuarioDni;
}
