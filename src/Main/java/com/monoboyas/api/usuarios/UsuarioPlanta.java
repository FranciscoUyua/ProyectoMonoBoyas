package com.monoboyas.api.usuarios;

import com.monoboyas.api.operacion.Planta;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("null")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public abstract class UsuarioPlanta extends Usuario {

    @ManyToOne
    @JoinColumn(name = "planta_id")
    private Planta planta;
}
