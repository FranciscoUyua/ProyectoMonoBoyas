package com.monoboyas.api.usuarios;

import com.monoboyas.api.operacion.Buque;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("OPERADOR_BUQUE")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OperadorBuque extends Usuario {

    @ManyToOne
    @JoinColumn(name = "buque_nro_imo")
    private Buque buque;
}
