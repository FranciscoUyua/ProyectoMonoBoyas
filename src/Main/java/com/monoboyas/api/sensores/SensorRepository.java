package com.monoboyas.api.sensores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, UUID> {
    List<Sensor> findByMonoboyaId(UUID monoboyaId);
    List<Sensor> findByBuqueNroIMO(String nroIMO);
}
