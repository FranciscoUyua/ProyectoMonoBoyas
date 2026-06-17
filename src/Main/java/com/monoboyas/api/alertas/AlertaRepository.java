package com.monoboyas.api.alertas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, UUID> {

    List<Alerta> findByOperacionId(UUID operacionId);

    List<Alerta> findByTipo(TipoAlerta tipo);

    List<Alerta> findByOperacionIdAndTipo(UUID operacionId, TipoAlerta tipo);
}
