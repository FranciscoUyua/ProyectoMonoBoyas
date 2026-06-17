package com.monoboyas.api.operacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperacionRepository extends JpaRepository<Operacion, UUID> {

    List<Operacion> findByEstado(EstadoOperacion estado);

    Optional<Operacion> findByMonoboyaIdAndEstado(UUID monoboyaId, EstadoOperacion estado);

    boolean existsByMonoboyaIdAndEstado(UUID monoboyaId, EstadoOperacion estado);
}
