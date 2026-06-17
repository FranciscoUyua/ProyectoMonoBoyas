package com.monoboyas.api.alertas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface UsuarioAlertaRepository extends JpaRepository<UsuarioAlerta, UsuarioAlertaId> {

    List<UsuarioAlerta> findByAlertaId(UUID alertaId);

    List<UsuarioAlerta> findByUsuarioDni(String dni);

    List<UsuarioAlerta> findByAlertaOperacionIdAndAlertaTipoAndReconocidaFalse(
        UUID operacionId,
        TipoAlerta tipo
    );
}
