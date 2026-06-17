package com.monoboyas.api.operacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface MonoboyaRepository extends JpaRepository<Monoboya, UUID> {
    List<Monoboya> findByEstado(EstadoMonoboya estado);
    List<Monoboya> findByPlantaId(UUID plantaId);
}
