package com.monoboyas.api.usuarios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("OPERADOR_LANCHA")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OperadorLancha extends Usuario {
}
