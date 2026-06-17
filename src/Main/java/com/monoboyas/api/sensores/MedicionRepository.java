package com.monoboyas.api.sensores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface MedicionRepository extends JpaRepository<Medicion, UUID> {

    List<Medicion> findBySensorIdOrderByTimestampDesc(UUID sensorId);

    List<Medicion> findByOperacionIdOrderByTimestampAsc(UUID operacionId);
}
